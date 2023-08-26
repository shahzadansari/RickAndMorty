@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id(libs.plugins.java.library.get().pluginId)
    id(libs.plugins.kotlin.jvm.get().pluginId)
    alias(libs.plugins.kotlin.serialization)
    id(libs.plugins.sqldelight.get().pluginId)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.core)
    implementation(projects.character.characterDomain)

    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.jsonSerialization)
    implementation(libs.sqldelight.runtime)
}

sqldelight {
    database("CharactersDatabase") {
        packageName = "com.example.character_datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}