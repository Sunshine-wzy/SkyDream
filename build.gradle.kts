plugins {
    val kotlinVersion = "1.5.10"
    kotlin("jvm") version kotlinVersion

    id("com.github.johnrengelman.shadow") version "5.2.0"
}
group = "io.github.sunshinewzy"
version = "4.4.1"

repositories {
    
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    mavenLocal()
    mavenCentral()

    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly(kotlin("stdlib"))

    compileOnly(fileTree(mapOf("dir" to "cores", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}


tasks {
    jar {
        archiveBaseName.set("SkyDream")
        archiveVersion.set(project.version.toString())
        destinationDirectory.set(file("F:/Kotlin/Debug/Spigot-1.16.5/plugins"))
    }
    
    shadowJar {
        archiveBaseName.set("SkyDream")
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")
        destinationDirectory.set(file("F:/Kotlin/Debug/Spigot-1.16.5/plugins"))
    }
    
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "name" to rootProject.name,
                "main" to "${project.group}.skydream.SkyDream",
                "version" to project.version
            )
        }
    }
}