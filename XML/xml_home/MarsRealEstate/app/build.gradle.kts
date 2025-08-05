plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kapt)
    id("kotlin-parcelize")
    alias(libs.plugins.navigation.safe.args)
}

android {
    namespace = "com.example.marsrealestate"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.marsrealestate"
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

    dataBinding {
        enable = true
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
    kapt(libs.glide.compiler)
}