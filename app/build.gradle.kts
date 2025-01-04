plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "com.stepikbrowser"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stepikbrowser"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    configurations.all {
        exclude(group = "com.intellij", module = "annotations")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
    viewBinding {
        enable = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.ktx)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.navigation)
    implementation(libs.bundles.firebase)
    implementation(libs.bundles.retrofit)

    // Feature Modules
    implementation(project(":feature:splash"))
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:home"))
    implementation(project(":feature:favorites"))
    implementation(project(":feature:profile"))

    implementation(project(":data:user"))
    implementation(project(":data:stepik"))
    implementation(project(":domain:auth"))
    implementation(project(":domain:stepik"))

    implementation(project(":core:util"))
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
