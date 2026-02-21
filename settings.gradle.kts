rootProject.name = "AnarchyMod"

pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.fabricmc.net/")
        }
        gradlePluginPortal()
    }
}


include("common")
include("mc-1.21.8")
include("mc-1.21.9")
include("mc-1.21.10")
include("mc-1.21.11")