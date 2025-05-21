plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("app.cash.sqldelight") version "2.0.2"
}

android {
    namespace = "com.example.meds_schedule"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.meds_schedule"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.sqlite.framework.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("app.cash.sqldelight:android-driver:2.0.2")

}


sqldelight {
    databases {
        create("EventDatabase") {
            packageName.set("com.example.meds_schedule")
        }
    }
}