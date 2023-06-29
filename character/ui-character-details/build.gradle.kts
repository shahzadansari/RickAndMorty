@file:Suppress("UnstableApiUsage")
plugins {
    id(Plugins.moduleLib)
    id(Plugins.kotlinAndroid)
}

android {
    namespace = "com.example.modularized_rickandmortyapp.character.ui_character_details"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
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
    composeOptions {
        kotlinCompilerExtensionVersion = ProjectConfig.kotlinCompilerExtensionVersion
    }
    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.components))
    implementation(project(Modules.characterInteractors))
    implementation(project(Modules.characterDomain))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)

    implementation(platform(Compose.composeBom))
    implementation(Compose.activity)
    implementation(Compose.material3)
    implementation(Compose.toolingPreview)
    implementation(Compose.ui)
}