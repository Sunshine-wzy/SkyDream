plugins {
    val kotlinVersion = "1.8.22"
    kotlin("jvm") version kotlinVersion

    id("com.github.johnrengelman.shadow") version "5.2.0"
}
group = "io.github.sunshinewzy"
version = "4.4.6"

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    maven("http://sacredcraft.cn:8081/repository/releases") { isAllowInsecureProtocol = true }
    
    mavenLocal()
    mavenCentral()

    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
    
    compileOnly("ink.ptms.core:v12001:12001:universal")
    compileOnly("ink.ptms.core:v12001:12001:mapped")

    compileOnly(fileTree(mapOf("dir" to "cores", "include" to listOf("*.jar"))))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}


tasks {
    jar {
        archiveBaseName.set("SkyDream")
        archiveVersion.set(project.version.toString())
    }
    
    shadowJar {
        archiveBaseName.set("SkyDream")
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("")

        relocate("kotlin.", "kotlin1822s.")
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