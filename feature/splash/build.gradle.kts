plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "com.stepikbrowser.feature.splash"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.appcompat)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.bundles.navigation)
    implementation(project(":core:ui"))
    implementation(project(":domain:auth"))
    implementation(libs.androidx.constraintlayout)
}