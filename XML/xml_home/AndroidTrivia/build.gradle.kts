// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // ... other plugins
    alias(libs.plugins.navigation.safe.args) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}