plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.firebase.crashlytics")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.24"
}

android {
    namespace = "com.andraganoid.verymuchtodo"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.andraganoid.verymuchtodo"
        minSdk = 26
        targetSdk = 35
        versionCode = 18
        versionName = "4.0.0.COMPOSE.1"
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
        jvmTarget = "19"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }


    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

//    fun Packaging.() {
//        resources {
//            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
//        }
//    }


}


dependencies {

    val composeVersion = "1.7.2"
    val lifecycleVersion = "2.8.6"
    val coroutinesVersion = "1.8.1"
    val materialVersion = "1.7.2"

  //  val composeBom = platform("androidx.compose:compose-bom:2024.01.00")
    implementation(platform("androidx.compose:compose-bom:2024.09.02"))

    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")
    // implementation("androidx.preference:preference-ktx:1.2.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("androidx.compose.material:material:$materialVersion")

    //Material icons - use with caution!
    implementation("androidx.compose.material:material-icons-extended:$materialVersion")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")

    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    //Lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    // Koin
    implementation("io.insert-koin:koin-android:3.6.0-wasm-alpha2")
    implementation("io.insert-koin:koin-androidx-compose:3.6.0-wasm-alpha2")


    implementation("androidx.appcompat:appcompat:1.6.1")//TODO REMOVE AT END
    implementation("com.google.android.material:material:1.11.0")//TODO REMOVE AT END


    //Gson
    implementation("com.google.code.gson:gson:2.10.1")//TODO REMOVE AT END

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")//TODO REMOVE AT END

    // Lifecycle
    //   implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    //   implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")//TODO REMOVE AT END
    //   implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")


    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.3.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")//TODO REMOVE AT END

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

}

kapt {
    correctErrorTypes = true
}
