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
val minecraftMappingVersion: String by project
val aquacultureVersion: String by project
val aquacultureVersionRange: String by project
val kotlinForForgeVersionRange: String by project
val jeiVersion: String by project
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

buildscript {
    repositories {
        maven {
            url = uri("https://maven.aliyun.com/repository/public/")
        }
        maven {
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
        }
    }
    dependencies {
        classpath("org.spongepowered:mixingradle:0.7-SNAPSHOT")
    }
}

plugins {
    java
    eclipse
    idea
    `maven-publish`
    `java-library`
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("net.neoforged.moddev") version "1.0.17"
    id("com.github.johnrengelman.shadow") version "7.1.2"
//    id("org.spongepowered.mixin") version "0.7.+"
    kotlin("jvm") version "1.9.23"
    kotlin("kapt") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    kotlin("plugin.lombok") version "1.9.23"
}

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/gradle-plugin")
    }
    maven {
        url = uri("https://jitpack.io")
    }
    maven("Kotlin for Forge") {
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
    maven("Jared's maven") {
        url = uri("https://maven.blamejared.com/")
    }
    maven("Aquaculture") {
        url = uri("https://girafi.dk/maven/")
    }
    maven("ModMaven") {
        // location of a maven mirror for JEI files, as a fallback
        url = uri("https://modmaven.dev")
    }
    maven {
        url = uri("https://www.cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
    mavenLocal()
    mavenCentral()
}

dependencies {
    val mixinProcessor = "org.spongepowered:mixin:0.8.5:processor"
    val aquaculture =
        "com.teammetallurgy.aquaculture:aquaculture2_${minecraftVersion}:${minecraftVersion}-${aquacultureVersion}"
    val kotlinforforge = "thedarkcolour:kotlinforforge-neoforge:5.5.0"
    val jeiCommonApi = "mezz.jei:jei-${minecraftVersion}-common-api:${jeiVersion}"
    val jeiForgeApi = "mezz.jei:jei-${minecraftVersion}-neoforge-api:${jeiVersion}"
    val jei = "mezz.jei:jei-${minecraftVersion}-neoforge:${jeiVersion}"
//    val configured = "curse.maven:configured-457570:5441232"

    val jable = "com.github.dsx137:jable:1.0.10"

    // Mixin
    annotationProcessor(mixinProcessor)

    // Aquaculture2
    implementation(aquaculture)

    // Kotlin for Forge
    implementation(kotlinforforge)

    // Jable
    implementation(jable)
    shade(jable)

    // Jei
    compileOnly(jeiCommonApi)
    compileOnly(jeiForgeApi)
    runtimeOnly(jei)

    // Configured
//    implementation(configured)
}

neoForge {
    version = neoforgeVersion

    accessTransformers { file("src/main/resources/META-INF/accesstransformer.cfg") }

    parchment.let {
        it.mappingsVersion = minecraftMappingVersion
        it.minecraftVersion = minecraftVersion
    }

    runs {
        create("client") {
            client()
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        create("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        create("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        create("data") {
            data()
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

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }
    mods {
        create(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}
sourceSets["main"].resources.srcDirs("src/generated/resources")

//mixin {
//    add(sourceSets.main.get(), "${modId}.refmap.json")
//    config("${modId}.mixins.json")
//}


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
sourceSets["main"].java.srcDirs(generateTemplates.map { it.destinationDir })
rootProject.idea.project.settings.taskTriggers.afterSync(generateTemplates)
project.eclipse.synchronizationTasks(generateTemplates)

tasks.withType(JavaCompile::class.java).configureEach {
    options.encoding = "UTF-8"
}

val processResourceConfig: ProcessResources.() -> Unit = {
    val targets = listOf("META-INF/neoforge.mods.toml", "pack.mcmeta")
    inputs.properties(props)

    filesMatching(targets) {
        expand(props)
    }
}

// 同一流程中只有一个processResources任务，所以runData必须和其他任务分开执行
tasks.processResources(processResourceConfig)

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

//val reobfShadowJar = reobf.create("shadowJar")

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

val oneStepBuild = tasks.create("oneStepBuild", ProcessResources::class) {
    group = "build"

    dependsOn("runData")
    finalizedBy("build")
    processResourceConfig()
}