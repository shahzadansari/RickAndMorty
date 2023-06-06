// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.moduleApp) version ProjectConfig.androidGradlePlugin apply false
    id(Plugins.moduleLib) version ProjectConfig.androidGradlePlugin apply false
    id(Plugins.kotlinAndroid) version ProjectConfig.kotlinAndroidVersion apply false
    id(Plugins.kotlinJvm) version ProjectConfig.kotlinJvmVersion apply false
}