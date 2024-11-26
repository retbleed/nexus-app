plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlinx-serialization")
}

android {
    namespace = "com.dasc.nexus"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dasc.nexus"
        minSdk = 30
        targetSdk = 34
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
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.places)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.datastore.preferences)


    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    /**
     * KTOR
     */

    implementation("io.ktor:ktor-client-core:3.0.0") // Or your desired Ktor 3.x version
    implementation("io.ktor:ktor-client-android:3.0.0")
    implementation("io.ktor:ktor-client-okhttp:3.0.0") // Or your desired Ktor 3.x version
    implementation("io.ktor:ktor-client-content-negotiation:3.0.0") // Or your desired Ktor 3.x version
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0") // Or your desired Ktor 3.x version
    implementation("io.ktor:ktor-client-logging:3.0.0") // Or your desired Ktor 3.x version
    implementation("io.ktor:ktor-client-plugins:3.0.0")

    /**
     * KOIN
     */

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)
}
