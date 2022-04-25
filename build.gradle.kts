buildscript {

    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath(kotlin("gradle-plugin", version = "1.6.21"))
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
        classpath("com.google.gms:google-services:4.3.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}