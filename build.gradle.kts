plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

version = "0.1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.1.0-SNAPSHOT")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.shadowJar {
    archiveBaseName = rootProject.name
    archiveClassifier = ""
    archiveVersion = version as String
}

tasks.named<Copy>("processResources") {
    inputs.property("internalVersion", version)
    filesMatching("plugin.yml") {
        expand("internalVersion" to version)
    }
}
