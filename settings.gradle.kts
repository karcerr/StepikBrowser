pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
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
    }
}

rootProject.name = "Stepik Browser"
include(":app")

include(":feature:splash")
include(":feature:onboarding")
include(":feature:auth")
include(":feature:home")
include(":feature:favorites")
include(":feature:profile")
include(":core:ui")
include(":data:user")
include(":domain:auth")
include(":domain:stepik")
include(":data:stepik")
