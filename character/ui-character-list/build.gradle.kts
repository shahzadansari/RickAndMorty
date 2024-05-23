@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.compose.compiler.get().pluginId)
}

android {
    namespace = "com.example.modularized_rickandmortyapp.character.ui_character_list"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.components)
    implementation(projects.character.characterInteractors)
    implementation(projects.character.characterDomain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.compose)
    implementation(libs.coil)
    implementation(libs.koin.android)
    implementation(libs.lifecycle.runtime.ktx)

    lintChecks(libs.compose.lint.checks)
}