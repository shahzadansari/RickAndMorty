@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
    id(libs.plugins.kotlin.serialization.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId) // needed only for non-primitive classes
}

android {
    namespace = "com.example.modularized_rickandmortyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.modularized_rickandmortyapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.components)
    implementation(projects.character.uiCharacterList)
    implementation(projects.character.uiCharacterDetails)
    implementation(projects.character.characterInteractors)
    implementation(projects.character.characterDomain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.compose)
    implementation(libs.compose.navigation)
    implementation(libs.coil)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.kotlinx.jsonSerialization)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.sqldelight.androidDriver)

    lintChecks(libs.compose.lint.checks)
}