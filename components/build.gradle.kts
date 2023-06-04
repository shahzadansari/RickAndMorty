@file:Suppress("UnstableApiUsage")
plugins {
    id(Plugins.moduleLib)
    id(Plugins.kotlinAndroid)
}

android {
    namespace = "com.example.modularized_rickandmortyapp.components"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)

    implementation(platform(Compose.composeBom))
    implementation(Compose.activity)
    implementation(Compose.material3)
    implementation(Compose.toolingPreview)
    implementation(Compose.ui)
}