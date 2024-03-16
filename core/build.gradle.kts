@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id(libs.plugins.java.library.get().pluginId)
    id(libs.plugins.kotlin.jvm.get().pluginId)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api(libs.kotlinx.collections.immutable)

    implementation(libs.kotlinx.jsonSerialization)
    implementation(libs.ktor.core)
}