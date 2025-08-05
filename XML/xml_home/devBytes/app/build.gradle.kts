plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.navigation.safe.args)
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android.gradlePlugin) // Apply the Hilt plugin here for the module
    alias(libs.plugins.ksp.plugin) // Apply the KSP plugin here for this module
    alias(libs.plugins.kapt) // Apply the KAPT plugin here for this module
}

android {
    namespace = "com.example.devbytes"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.devbytes"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // ⬇️ SERIALIZATION
    // Core library for converting Kotlin objects to/from JSON
    implementation(libs.kotlinx.serialization.json)

    // ⬇️ NETWORKING
    // The main Retrofit library
    implementation(libs.retrofit)
    // The official converter for Kotlinx Serialization
    implementation(libs.retrofit.converter.kotlinx.serialization)

    // glide
    implementation(libs.glide)
    ksp(libs.glide.compiler)

    //  room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //timber
    implementation(libs.timber)

    //hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler) // Use 'ksp' for the compiler if using KSP
}