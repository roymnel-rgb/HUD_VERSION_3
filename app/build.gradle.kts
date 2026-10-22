// File: app/build.gradle.kts
plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.hudnavigation"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hudnavigation"
        minSdk = 24
        targetSdk = 34
        versionCode = 3
        versionName = "3.0.0"

        // Optional: prevent build if local.properties is missing
        val localProps = rootProject.file("local.properties")
        if (!localProps.exists()) {
            throw GradleException("local.properties missing – define your keystore or SDK paths.")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Use the version catalog for dependencies (see libs.versions.toml)
    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(libs.compose.ui)
        implementation(libs.compose.material3)
        implementation(libs.compose.ui.tooling.preview)
        debugImplementation(libs.compose.ui.tooling)
    }

    // Enable Java 17 (required for AGP 8+)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
