import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.4.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("com.github.johnrengelman.shadow") version "5.2.0"
}
group = "io.github.sunshinewzy"
version = "4.0"

repositories {
    mavenLocal()
    mavenCentral()

    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("http://maven.aliyun.com/nexus/content/groups/public")
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    jcenter()
}

dependencies {
    testImplementation(kotlin("test-junit"))

    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")

    compileOnly(fileTree(mapOf("dir" to "cores", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}