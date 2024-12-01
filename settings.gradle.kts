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

rootProject.name = "Stepik Browser"
include(":app")
include(":network")
include(":domain")
include(":core")
include(":core_ui")
include(":data")
include(":feature_onboarding")
include(":feature_auth")
include(":feature_main")
include(":feature_favorites")
include(":feature_account")
include(":feature_splash")
include(":feature_splash")
