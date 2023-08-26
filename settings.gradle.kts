@file:Suppress("UnstableApiUsage")
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Modularized-RickAndMortyApp"
include(":app")
include(":character")
include(":character:character-datasource")
include(":character:character-datasource-test")
include(":character:character-domain")
include(":character:character-interactors")
include(":character:ui-character-details")
include(":character:ui-character-list")
include(":components")
include(":core")

// Generate type safe accessors when referring to other projects e.g.
// Before -> implementation(project(":core"))
// After -> implementation(projects.core)
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")