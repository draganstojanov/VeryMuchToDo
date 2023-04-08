plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.andraganoid.verymuchtodo"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.andraganoid.verymuchtodo"
        minSdk = 23
        targetSdk = 33
        versionCode = 17
        versionName = "3.4.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
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
            buildConfigField("boolean", CRASHLYTICS, "true")
        }

        create("staging") {
            dimension = "version"
            buildConfigField("boolean", CRASHLYTICS, "true")
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


    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }


}


dependencies {

    val composeVersion = "1.4.0"
    val lifecycleVersion = "2.6.1"
    val coroutinesVersion = "1.6.4"
    val materialVersion = "1.4.0"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.activity:activity-compose:1.7.0")
   // implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material:material:$materialVersion")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.0")

    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")

    // Koin
    implementation("io.insert-koin:koin-android:3.4.0")


    implementation("androidx.appcompat:appcompat:1.6.1")//TODO REMOVE AT END
    implementation("com.google.android.material:material:1.8.0")//TODO REMOVE AT END


    //Gson
    implementation("com.google.code.gson:gson:2.10.1")//TODO REMOVE AT END

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")//TODO REMOVE AT END

    // Lifecycle
 //   implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
 //   implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")//TODO REMOVE AT END
 //   implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")



    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:31.4.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")//TODO REMOVE AT END

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

}

kapt {
    correctErrorTypes = true
}