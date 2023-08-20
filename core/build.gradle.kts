plugins {
    id(Plugins.moduleJavaLib)
    id(Plugins.kotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(KotlinX.jsonSerialization)
    implementation(Ktor.core)
}