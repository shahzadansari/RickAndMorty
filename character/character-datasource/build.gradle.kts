plugins {
    id(Plugins.moduleJavaLib)

    id(Plugins.kotlinJvm)
    kotlin(Plugins.kotlinSerialization) version ProjectConfig.kotlinVersion

    id(SqlDelight.plugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.characterDomain))

    implementation(KotlinX.jsonSerialization)

    implementation(Ktor.core)
    implementation(Ktor.androidClient)
    implementation(Ktor.clientSerialization)
    implementation(Ktor.contentNegotiation)
    implementation(Ktor.logging)

    implementation(SqlDelight.runtime)
}

sqldelight {
    database("CharactersDatabase") {
        packageName = "com.example.character_datasource.cache"
        sourceFolders = listOf("sqldelight")
    }
}