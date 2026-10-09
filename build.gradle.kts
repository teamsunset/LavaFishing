import club.redux.sunset.lavafishing.Deps
import club.redux.sunset.lavafishing.Props
import club.redux.sunset.lavafishing.Utils.relocateToShadowed
import com.github.jengelman.gradle.plugins.shadow.transformers.KotlinModuleMetadataTransformer
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

val shade = configurations.create("shade")
val fullShade = configurations.create("fullShade")

val mainSourceSet = extensions.getByType(JavaPluginExtension::class.java).sourceSets.getByName("main")

val javaVersion = JavaLanguageVersion.of(25)

version = "${Props.MINECRAFT_VERSION}-${Props.MOD_VERSION}"
group = Props.MOD_GROUP_ID

tasks.register("exportVersion") {
    doLast {
        println("version=${version}")
        println("minecraft_version=${Props.MINECRAFT_VERSION}")
    }
}

base.archivesName.set(Props.MOD_ID)
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
    id("com.gradleup.shadow")
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.4.1"
    id("net.neoforged.moddev") version "2.0.142"
    kotlin("jvm") version "2.4.0"
    kotlin("kapt") version "2.4.0"
    kotlin("plugin.serialization") version "2.4.0"
    kotlin("plugin.lombok") version "2.4.0"
}

repositories {
    google()
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
    // JUnit
    testImplementation(Deps.JUNIT_API)
    testRuntimeOnly(Deps.JUNIT_ENGINE)
    testRuntimeOnly(Deps.JUNIT_LAUNCHER)

    // Mixin
    annotationProcessor(Deps.MIXIN_PROCESSOR)

    // Aquaculture2
    implementation(Deps.AQUACULTURE)

    // Kotlin for Forge
    implementation(Deps.KOTLIN_FOR_FORGE)

    // Jei
    compileOnly(Deps.JEI_FORGE_API)
    runtimeOnly(Deps.JEI)

    // AppleSkin
    runtimeOnly(Deps.APPLE_SKIN)
}

neoForge {
    version = Props.NEOFORGE_VERSION

    runs {
        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            systemProperty("forge.logging.console.level", "debug")
            systemProperty("neoforge.enabledGameTestNamespaces", Props.MOD_ID)
        }
        register("client") { client() }
        register("server") { server() }
        register("gameTestServer") { type.set("gameTestServer") }
        fun dataRunArguments(modId: String, outputDirectory: String) = listOf(
            "--mod", modId,
            "--all",
            "--output", file(outputDirectory).absolutePath,
            "--existing", file("src/main/resources/").absolutePath,
        )
        register("clientData") {
            clientData()
            programArguments.addAll(dataRunArguments(Props.MOD_ID, "src/generated/resources/client"))
        }
        register("serverData") {
            serverData()
            programArguments.addAll(dataRunArguments(Props.MOD_ID, "src/generated/resources/server"))
        }
    }

    mods {
        register(Props.MOD_ID) {
            sourceSet(mainSourceSet)
        }
    }

    unitTest {
        enable()
        testedMod = mods.getByName(Props.MOD_ID)
    }

}

val props = Props.toMap()

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
                "Specification-Title" to Props.MOD_ID,
                "Specification-Vendor" to Props.MOD_AUTHORS,
                "Specification-Version" to "1", // We are version 1 of ourselves
                "Implementation-Title" to project.name,
                "Implementation-Version" to Props.MOD_VERSION,
                "Implementation-Vendor" to Props.MOD_AUTHORS,
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

    relocateToShadowed("${Props.MOD_GROUP_ID}.${Props.MOD_ID}.shadowed", "com.github", "okio")
}

tasks.build { dependsOn("shadowJar") }
