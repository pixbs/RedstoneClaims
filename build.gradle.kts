import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val projectVersion = "0.1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.9-beta1")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.shadowJar {
    archiveBaseName.set(rootProject.name)
    archiveClassifier.set("")
    archiveVersion.set(projectVersion)
}

tasks.processResources {
    val props = mapOf("projectVersion" to projectVersion)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.register<Copy>("copyFiles") {
    from("./build/libs")
    into("D:/Minecraft Server/plugins")
    include("*.jar")
    dependsOn("shadowJar")
}

tasks.getByName<ShadowJar>("shadowJar").finalizedBy("copyFiles")
