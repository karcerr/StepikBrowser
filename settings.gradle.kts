pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "Stepik Browser"
include(":app")

include(":feature:splash")
include(":feature:onboarding")
include(":feature:auth")
include(":feature:home")
include(":feature:bookmarks")
include(":feature:profile")
include(":core:ui")
include(":data:user")
include(":domain:auth")
include(":domain:stepik")
include(":data:stepik")
include(":core:util")
