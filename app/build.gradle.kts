plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.andraganoid.verymuchtodo"
        minSdk = 23
        targetSdk = 32
        versionCode = 16
        versionName = "3.4.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable=false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }


    flavorDimensions += "version"



    productFlavors {
        val CRASHLYTICS = "CRASHLYTICS"
        create("production") {
            dimension = "version"
            buildConfigField ("boolean", CRASHLYTICS, "true")
        }

        create("staging") {
            dimension = "version"
            buildConfigField ("boolean", CRASHLYTICS, "true")
        }
    }


    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}



dependencies {

    val lifecycleVersion = "2.4.1"
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0-native-mt")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.2")

    // AndroidX
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.core:core-ktx:1.7.0")

    implementation("com.google.android.material:material:1.5.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")

    // Koin
    implementation("io.insert-koin:koin-android:3.1.5")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:29.0.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")

}
