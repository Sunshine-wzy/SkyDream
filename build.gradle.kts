plugins {
    val kotlinVersion = "1.4.30"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("com.github.johnrengelman.shadow") version "5.2.0"
}
group = "io.github.sunshinewzy"
version = "4.0"

kotlin {
    target { 
        
    }
}

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public/")
    }
    mavenLocal()
    mavenCentral()
    jcenter()
    
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

dependencies {
    testImplementation(kotlin("test-junit"))

    compileOnly(fileTree(mapOf("dir" to "cores", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

tasks {
    jar {
        archiveBaseName.set("SkyDream")
        archiveVersion.set(project.version.toString())
        destinationDirectory.set(file("F:/Kotlin/Debug/Spigot-1.16.5/plugins"))
//        destinationDirectory.set(file("F:/Java/Debug/Spigot-1.12/plugins"))
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
}