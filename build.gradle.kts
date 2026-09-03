import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.github.jengelman.gradle.plugins.shadow.transformers.KotlinModuleMetadataTransformer
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

val minecraftVersion = providers.gradleProperty("minecraftVersion").get()
val minecraftVersionRange = providers.gradleProperty("minecraftVersionRange").get()
val neoforgeVersion = providers.gradleProperty("neoforgeVersion").get()
val neoforgeVersionRange = providers.gradleProperty("neoforgeVersionRange").get()
val modLoader = providers.gradleProperty("modLoader").get()
val modLoaderVersionRange = providers.gradleProperty("modLoaderVersionRange").get()
val aquacultureVersion = providers.gradleProperty("aquacultureVersion").get()
val aquacultureVersionRange = providers.gradleProperty("aquacultureVersionRange").get()
val kotlinForForgeVersion = providers.gradleProperty("kotlinForForgeVersion").get()
val kotlinForForgeVersionRange = providers.gradleProperty("kotlinForForgeVersionRange").get()
val jeiVersion = providers.gradleProperty("jeiVersion").get()
val modId = providers.gradleProperty("modId").get()
val modName = providers.gradleProperty("modName").get()
val modLicense = providers.gradleProperty("modLicense").get()
val modVersion = providers.gradleProperty("modVersion").get()
val modGroupId = providers.gradleProperty("modGroupId").get()
val modAuthors = providers.gradleProperty("modAuthors").get()
val modCredits = providers.gradleProperty("modCredits").get()
val modDescription = providers.gradleProperty("modDescription").get()

val shade = configurations.create("shade")
val fullShade = configurations.create("fullShade")

configurations.create("runtimeMaven")
configurations.create("providedMaven")
configurations.create("compileMaven")

val mainSourceSet = extensions.getByType(JavaPluginExtension::class.java).sourceSets.getByName("main")

val javaVersion = JavaLanguageVersion.of(25)

version = "$minecraftVersion-$modVersion"
group = modGroupId

base.archivesName.set(modId)
java.toolchain.languageVersion.set(javaVersion)
kapt.keepJavacAnnotationProcessors = true

idea.module.isDownloadJavadoc = true
idea.module.isDownloadSources = true

plugins {
    java
    eclipse
    idea
    `maven-publish`
    `java-library`
    id("com.gradleup.shadow") version "9.6.0"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.4.1"
    id("net.neoforged.moddev") version "2.0.142"
    kotlin("jvm") version "2.4.0"
    kotlin("kapt") version "2.4.0"
    kotlin("plugin.serialization") version "2.4.0"
    kotlin("plugin.lombok") version "2.4.0"
}

repositories {
    maven("Kotlin for Forge") { url = uri("https://thedarkcolour.github.io/KotlinForForge/") }
    maven("Jared's maven") { url = uri("https://maven.blamejared.com/") }
    maven("AppleSkin") { url = uri("https://maven.ryanliptak.com/") }
    maven {
        url = uri("https://www.cursemaven.com")
        content { includeGroup("curse.maven") }
    }
    mavenCentral()
}

dependencies {
    val mixinProcessor = "org.spongepowered:mixin:0.8.7:processor"
    val aquaculture = "curse.maven:aquaculture-60028:8192770"
    val kotlinforforge = "thedarkcolour:kotlinforforge-neoforge:${kotlinForForgeVersion}"
    val jeiForgeApi = "mezz.jei:jei-${minecraftVersion}-neoforge-api:${jeiVersion}"
    val jei = "mezz.jei:jei-${minecraftVersion}-neoforge:${jeiVersion}"
    val appleSkin = "squeek.appleskin:appleskin-neoforge:mc26.1-3.0.9"

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:6.1.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:6.1.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Mixin
    annotationProcessor(mixinProcessor)

    // Aquaculture2
    implementation(aquaculture)

    // Kotlin for Forge
    implementation(kotlinforforge)

    // Jei
    compileOnly(jeiForgeApi)
    runtimeOnly(jei)

    // AppleSkin
    runtimeOnly(appleSkin)
}

fun dataRunArguments(outputDirectory: String) = listOf(
    "--mod",
    modId,
    "--all",
    "--output",
    file(outputDirectory).absolutePath,
    "--existing",
    file("src/main/resources/").absolutePath,
)

neoForge {
    version = neoforgeVersion

    runs {
        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            systemProperty("forge.logging.console.level", "debug")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }
        register("client") { client() }
        register("server") { server() }
        register("gameTestServer") { type.set("gameTestServer") }
        register("clientData") {
            clientData()
            programArguments.addAll(dataRunArguments("src/generated/resources/client"))
        }
        register("serverData") {
            serverData()
            programArguments.addAll(dataRunArguments("src/generated/resources/server"))
        }
    }

    mods {
        register(modId) {
            sourceSet(mainSourceSet)
        }
    }

    unitTest {
        enable()
        testedMod = mods.getByName(modId)
    }

}

val props = mapOf(
    "minecraft_version" to minecraftVersion,
    "minecraft_version_range" to minecraftVersionRange,
    "neoforge_version" to neoforgeVersion,
    "neoforge_version_range" to neoforgeVersionRange,
    "mod_loader" to modLoader,
    "mod_loader_version_range" to modLoaderVersionRange,
    "mod_id" to modId,
    "mod_name" to modName,
    "mod_license" to modLicense,
    "mod_version" to modVersion,
    "mod_authors" to modAuthors,
    "mod_description" to modDescription,
    "aquaculture_version_range" to aquacultureVersionRange,
    "kotlin_for_forge_version_range" to kotlinForForgeVersionRange,
    "mod_credits" to modCredits
)

val generateTemplates = tasks.register<Copy>("generateTemplates") {
    val src = file("src/main/templates/java")
    val dst = layout.buildDirectory.dir("generated/sources/templates/java")
    inputs.properties(props)

    from(src)
    into(dst)
    expand(props)
}
sourceSets["main"].resources.srcDirs(
    "src/generated/resources/client",
    "src/generated/resources/server",
)
sourceSets["main"].java.srcDirs(generateTemplates.map { it.destinationDir })
rootProject.idea.project.settings.taskTriggers.afterSync(generateTemplates)
project.eclipse.synchronizationTasks(generateTemplates)

tasks.withType(JavaCompile::class.java).configureEach { options.encoding = "UTF-8" }

tasks.processResources {
    val targets = listOf("META-INF/neoforge.mods.toml")
    inputs.properties(props)

    filesMatching(targets) {
        expand(props)
    }
}

tasks.test {
    useJUnitPlatform()
    failOnNoDiscoveredTests = false
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Specification-Title" to modId,
                "Specification-Vendor" to modAuthors,
                "Specification-Version" to "1", // We are version 1 of ourselves
                "Implementation-Title" to project.name,
                "Implementation-Version" to modVersion,
                "Implementation-Vendor" to modAuthors,
                "Implementation-Timestamp" to DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
                    .format(Date().toInstant().atOffset(ZoneOffset.UTC))
            )
        )
    }
}

tasks.shadowJar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    filesMatching(listOf("META-INF/services/**", "**/*.kotlin_module")) {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
    mergeServiceFiles()
    transform(KotlinModuleMetadataTransformer::class.java)
    minimize {
        fullShade.dependencies.forEach { exclude(dependency(it)) }
    }

    configurations = listOf(shade)

    fun ShadowJar.relocateToShadowed(vararg paths: String): ShadowJar {
        paths.forEach { relocate(it, "${modGroupId}.${modId}.shadowed.$it") }
        return this
    }

    relocateToShadowed("com.github")
}

tasks.build { dependsOn("shadowJar") }
