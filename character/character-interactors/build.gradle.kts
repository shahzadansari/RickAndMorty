plugins {
    id(Plugins.moduleJavaLib)
    id(Plugins.kotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.characterDomain))
    implementation(project(Modules.characterDataSource))

    implementation(KotlinX.coroutinesCore)
}