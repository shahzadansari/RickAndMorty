@file:Suppress("UnstableApiUsage")
plugins {
    id(Plugins.moduleApp)
    id(Plugins.hiltAndroid)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
}

android {
    namespace = "com.example.modularized_rickandmortyapp"
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.applicationId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

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
        kotlinCompilerExtensionVersion = ProjectConfig.kotlinCompilerExtensionVersion
    }
    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.components))
    implementation(project(Modules.character_ui_list))
    implementation(project(Modules.character_ui_details))
    implementation(project(Modules.characterInteractors))
    implementation(project(Modules.characterDomain))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntimeKtx)

    implementation(Coil.coil)

    implementation(platform(Compose.composeBom))
    implementation(Compose.activity)
    implementation(Compose.material3)
    implementation(Compose.toolingPreview)
    implementation(Compose.ui)
    implementation(Compose.navigation)
    implementation(Compose.hiltNavigation)

    implementation(Hilt.hiltAndroid)
    kapt(Hilt.hiltCompiler)

    implementation(Paging.runtime)
    implementation(Paging.compose)

    implementation(SqlDelight.androidDriver)
}