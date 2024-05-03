import groovy.util.Node
import groovy.util.NodeList
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

val javaVersion = JavaLanguageVersion.of(17)

version = "$minecraftVersion-$modVersion"
group = modGroupId

base.archivesName.set(modId)
java.toolchain.languageVersion.set(javaVersion)
kapt.keepJavacAnnotationProcessors = true

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
    val mc = "net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}"
    val jable = "com.github.dsx137:jable:1.0.10"
    val lombok = "org.projectlombok:lombok:1.18.30"
    val aquaculture = "com.github.TeamSunset:Aquaculture:aeb4f5516b"
    val kotlinforforge = "thedarkcolour:kotlinforforge:4.10.0"
    val jeiCommonApi = "mezz.jei:jei-${minecraftVersion}-common-api:${jeiVersion}"
    val jeiForgeApi = "mezz.jei:jei-${minecraftVersion}-forge-api:${jeiVersion}"
    val jei = "mezz.jei:jei-${minecraftVersion}-forge:${jeiVersion}"

    // Minecraft
    minecraft(mc)

    // Aquaculture2
    implementation(aquaculture)
    compileMaven(aquaculture)

    // Jei
    compileOnly(fg.deobf(jeiCommonApi))
    compileOnly(fg.deobf(jeiForgeApi))
    runtimeOnly(fg.deobf(jei))

    // Kotlin for Forge
    implementation(kotlinforforge)
    compileMaven(kotlinforforge)

    // Lombok
    compileOnly(lombok)
    annotationProcessor(lombok)
    kapt(lombok)

    // Jable
    minecraftLibrary(jable)
    shade(jable)
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

val createRunDataAny = { name: String ->
    tasks.create("runData$name") {
        group = "forgegradle runs"
        dependsOn("runData")
        finalizedBy("run$name")

        doLast {
            tasks.getByName("prepareRun${name}Compile").outputs.upToDateWhen { false }
        }
    }
}

val runDataClient = createRunDataAny("Client")
val runDataServer = createRunDataAny("Server")
val runDataGameTestServer = createRunDataAny("GameTestServer")

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

    relocate("com.github", "${modGroupId}.${modId}.shadowed.com.github")
}

val reobfShadowJar = reobf.create("shadowJar") {
}

tasks.jar {
    dependsOn("runData")
    doFirst {
        val modToml = file("build/resources/main/META-INF/mods.toml")
        modToml.writeText(
            modToml.readText().replace(
                "mandatory = false # kff template replace",
                "mandatory = true"
            )
        )
    }
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
