plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.googleServices)
}

android {
    namespace = "com.stepikbrowser"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stepikbrowser"
        minSdk = 23
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.ktx)


    implementation(libs.bundles.hilt)
    implementation(libs.bundles.navigation)

    // Feature Modules
    implementation(project(":feature_splash"))
    implementation(project(":feature_onboarding"))
    implementation(project(":feature_auth"))
    implementation(project(":feature_main"))
    implementation(project(":feature_favorites"))
    implementation(project(":feature_account"))


    // Library Modules
    implementation(project(":core"))
    implementation(project(":core_ui"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":network"))
}