import groovy.util.Node
import groovy.util.NodeList
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

val minecraftVersion: String by project
val minecraftVersionRange: String by project
val neoforgeVersion: String by project
val neoforgeVersionRange: String by project
val modLoader: String by project
val modLoaderVersionRange: String by project
val minecraftMappingChannel: String by project
val minecraftMappingMinecraftVersion: String by project
val minecraftMappingVersion: String by project
val aquacultureVersion: String by project
val aquacultureVersionRange: String by project
val kotlinForForgeVersion: String by project
val kotlinForForgeVersionRange: String by project
val jeiVersion: String by project
val jableVersion: String by project
val modId: String by project
val modName: String by project
val modLicense: String by project
val modVersion: String by project
val modGroupId: String by project
val modAuthors: String by project
val modCredits: String by project
val modDescription: String by project

val shade: Configuration by configurations.creating
val fullShade: Configuration by configurations.creating

val runtimeMaven: Configuration by configurations.creating
val providedMaven: Configuration by configurations.creating
val compileMaven: Configuration by configurations.creating
val libraries: Configuration by configurations.creating

val javaVersion = JavaLanguageVersion.of(21)

version = "$minecraftVersion-$modVersion"
group = modGroupId

base.archivesName.set(modId)
java.toolchain.languageVersion.set(javaVersion)
kapt.keepJavacAnnotationProcessors = true

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

plugins {
    java
    eclipse
    idea
    `maven-publish`
    `java-library`
    id("com.gradleup.shadow") version "8.3.2"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("net.neoforged.gradle.userdev") version "7.0.145"
    id("net.neoforged.gradle.mixin") version "7.0.145"
    kotlin("jvm") version "1.9.23"
    kotlin("kapt") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    kotlin("plugin.lombok") version "1.9.23"
}

repositories {
    maven { url = uri("https://maven.aliyun.com/repository/public/") }
    maven { url = uri("https://jitpack.io") }
    maven("Kotlin for Forge") { url = uri("https://thedarkcolour.github.io/KotlinForForge/") }
    maven("Jared's maven") { url = uri("https://maven.blamejared.com/") }
    maven("Aquaculture") { url = uri("https://girafi.dk/maven/") }
    maven("ModMaven") { url = uri("https://modmaven.dev") }
    maven {
        url = uri("https://www.cursemaven.com")
        content { includeGroup("curse.maven") }
    }
    mavenLocal()
    mavenCentral()
}

dependencies {
    val mixinProcessor = "org.spongepowered:mixin:0.8.5:processor"
    val aquaculture =
        "com.teammetallurgy.aquaculture:aquaculture2_${minecraftVersion}:${minecraftVersion}-${aquacultureVersion}"
    val kotlinforforge = "thedarkcolour:kotlinforforge-neoforge:${kotlinForForgeVersion}"
    val jeiForgeApi = "mezz.jei:jei-${minecraftVersion}-neoforge-api:${jeiVersion}"
    val jei = "mezz.jei:jei-${minecraftVersion}-neoforge:${jeiVersion}"

    val jable = "com.github.dsx137:jable:${jableVersion}"

    // NeoForge
    implementation("net.neoforged:neoforge:${neoforgeVersion}")

    // JUnit
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")

    // Mixin
    annotationProcessor(mixinProcessor)

    // Aquaculture2
    implementation(aquaculture)

    // Kotlin for Forge
    implementation(kotlinforforge)

    // Jable
    implementation(jable)
    libraries(jable)
    shade(jable)

    // Jei
    compileOnly(jeiForgeApi)
    runtimeOnly(jei)
}

runs {
    configureEach {
        systemProperty("forge.logging.markers", "REGISTRIES")
        systemProperty("forge.logging.console.level", "debug")
        modSource(sourceSets["main"])
    }

    named("client") {
        systemProperty("neoforge.enabledGameTestNamespaces", modId)
    }

    named("server") {
        systemProperty("neoforge.enabledGameTestNamespaces", modId)
        programArgument("--nogui")
    }

    named("gameTestServer") {
        systemProperty("neoforge.enabledGameTestNamespaces", modId)
    }

    named("data") {
        programArguments.addAll(
            "--mod",
            modId,
            "--all",
            "--output",
            file("src/generated/resources/").absolutePath,
            "--existing",
            file("src/main/resources/").absolutePath
        )
    }
}

minecraft {
    accessTransformers { file("src/main/resources/META-INF/accesstransformer.cfg") }
}
mixin {
    config("${modId}.mixins.json")
}
subsystems {
    parchment.let {
        it.minecraftVersion = minecraftMappingMinecraftVersion
        it.mappingsVersion = minecraftMappingVersion
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

val generateTemplates by tasks.registering(Copy::class) {
    val src = file("src/main/templates/java")
    val dst = layout.buildDirectory.dir("generated/sources/templates/java")
    inputs.properties(props)
    outputs.upToDateWhen { false }

    from(src)
    into(dst)
    expand(props)
}
sourceSets["main"].resources.srcDirs("src/generated/resources")
sourceSets["main"].java.srcDirs(generateTemplates.map { it.destinationDir })
rootProject.idea.project.settings.taskTriggers.afterSync(generateTemplates)
project.eclipse.synchronizationTasks(generateTemplates)

tasks.withType(JavaCompile::class.java).configureEach {
    options.encoding = "UTF-8"
}

val processResourcesConfig: ProcessResources.() -> Unit = {
    val targets = listOf("META-INF/neoforge.mods.toml", "pack.mcmeta")
    inputs.properties(props)

    filesMatching(targets) {
        expand(props)
    }
}

// 同一流程中只有一个processResources任务，所以runData必须和其他任务分开执行
tasks.processResources { processResourcesConfig() }

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
    mergeServiceFiles()
    minimize()
    minimize {
        fullShade.dependencies.forEach {
            exclude(dependency(it))
        }
    }

    configurations = listOf(shade, fullShade)

    relocate("com.github", "${modGroupId}.${modId}.shadowed.com.github")
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.withType(GenerateModuleMetadata::class.java) {
    enabled = false
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = project.archivesName.get()
            groupId = project.group.toString()
            version = project.version.toString()
            pom {
                name.set(modName)
                licenses {
                    license {
                        name.set(modLicense)
                    }
                }
                withXml {
                    asNode().remove((asNode().get("dependencies") as NodeList).first() as Node)

                    asNode().appendNode("dependencies").apply {
                        val appendDependency = { configuration: Configuration, scope: String ->
                            configuration.dependencies.forEach {
                                appendNode("dependency").apply {
                                    appendNode("groupId", it.group)
                                    appendNode("artifactId", it.name)
                                    appendNode("version", it.version)
                                    appendNode("scope", scope)
                                }
                            }
                        }

                        appendDependency(compileMaven, "compile")
                        appendDependency(runtimeMaven, "runtime")
                        appendDependency(providedMaven, "provided")
                    }
                }
            }
        }
    }
}

/*---TeaCon---*/

fun createAfterRunData(task: String) =
    tasks.create("${task}AfterRunData", ProcessResources::class) {
        group = "After runData"
        dependsOn("runData")
        finalizedBy(task)
        tasks.jar.get().mustRunAfter(this)
        tasks.test.get().mustRunAfter(this)
        tasks.shadowJar.get().mustRunAfter(this)

        val originDirs = sourceSets["main"].resources.srcDirs.map { it.relativeTo(projectDir) }
        val outputDir = file("build/resources/main")

        doFirst {
            delete(outputDir)
        }
        from(originDirs) {
            exclude {
                it.isDirectory && it.name.startsWith(".")
            }
        }
        into(outputDir)
        processResourcesConfig()
    }

val buildAfterRunData = createAfterRunData("build")
val runClientAfterRunData = createAfterRunData("runClient")
val runServerAfterRunData = createAfterRunData("runServer")
