import net.minecraftforge.gradle.common.util.RunConfig
import org.jetbrains.gradle.ext.settings
import org.jetbrains.gradle.ext.taskTriggers
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

val minecraftVersion: String by project
val minecraftVersionRange: String by project
val forgeVersion: String by project
val forgeVersionRange: String by project
val modLoader: String by project
val modLoaderVersionRange: String by project
val minecraftMappingChannel: String by project
val minecraftMappingVersion: String by project
val aquacultureVersionRange: String by project
val kotlinForForgeVersionRange: String by project
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

plugins {
    java
    eclipse
    idea
    `maven-publish`
    id("net.minecraftforge.gradle") version "[6.0,6.2)"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    kotlin("jvm") version "1.9.23"
    kotlin("kapt") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
}

repositories {
    maven {
        url = uri("https://www.cursemaven.com")
        content {
            includeGroup("curse.maven")
        }
    }
    maven {
        name = "Kotlin for Forge"
        setUrl("https://thedarkcolour.github.io/KotlinForForge/")
    }
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    maven{
        url = uri("https://maven.aliyun.com/repository/gradle-plugin")
    }
    maven("jitpack") {
        url = uri("https://jitpack.io")
    }
    mavenLocal()
    mavenCentral()
}

dependencies {
    val jable = "com.github.dsx137:jable:1.0.10"
    val lombok = "org.projectlombok:lombok:1.18.30"

    // Minecraft
    minecraft("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")

    // Aquaculture2
    implementation(fg.deobf("curse.maven:aquaculture-60028:4921323"))

    // Kotlin for Forge
    implementation("thedarkcolour:kotlinforforge:4.10.0")

    // Lombok
    compileOnly(lombok)
    annotationProcessor(lombok)
    kapt(lombok)

    // Jable
    minecraftLibrary(jable)
    shade(jable)
}

val javaVersion = JavaLanguageVersion.of(17)

version = "$minecraftVersion-$modVersion"
group = modGroupId

base {
    archivesName.set(modId)
}

java {
    toolchain.languageVersion.set(javaVersion)
}

kapt {
    keepJavacAnnotationProcessors = true
}

minecraft {
    mappings(minecraftMappingChannel, minecraftMappingVersion)

    copyIdeResources = true

    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    fun createMinecraftRun(vararg names: String, additionalConfig: RunConfig.() -> Unit = { }) {
        for (name in names) {
            minecraft.runs.create(name) {
                workingDirectory(project.file("run"))

                property("forge.logging.markers", "REGISTRIES")
                property("forge.logging.console.level", "debug")

                this.additionalConfig()

                mods {
                    create(modId) {
                        source(sourceSets["main"])
                    }
                }
            }
        }
    }

    createMinecraftRun("client", "server", "gameTestServer") {
        property("forge.enabledGameTestNamespaces", modId)
    }

    createMinecraftRun("data") {
        args(
            "--mod",
            modId,
            "--all",
            "--output",
            file("src/generated/resources/"),
            "--existing",
            file("src/main/resources/")
        )
    }
}

sourceSets["main"].resources.srcDirs("src/generated/resources")

val runDataAny = { name: String ->
    tasks.create("runData$name") {
        group = "forgegradle runs"
        dependsOn("runData")
        finalizedBy("run$name")
    }
}

val runDataClient = runDataAny("Client")
val runDataServer = runDataAny("Server")
val runDataGameTestServer = runDataAny("GameTestServer")

val props = mapOf(
    "minecraft_version" to minecraftVersion,
    "minecraft_version_range" to minecraftVersionRange,
    "forge_version" to forgeVersion,
    "forge_version_range" to forgeVersionRange,
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

tasks.processResources {
    val targets = listOf("META-INF/mods.toml", "pack.mcmeta")

    inputs.properties(props)
//    replaceProperties["project"] = project

    filesMatching(targets) {
        expand(props)
    }
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
    mergeServiceFiles()
    minimize()
    minimize {
        fullShade.dependencies.forEach {
            exclude(dependency(it))
        }
    }

    configurations = listOf(shade, fullShade)

    relocate("com.github", "${modGroupId}.shadowed.com.github")
}

val reobfShadowJar = reobf.create("shadowJar") {
}

tasks.jar {
    dependsOn("runData")
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
            }
        }
    }
}
