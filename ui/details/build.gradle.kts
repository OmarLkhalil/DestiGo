@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidlibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.firebase)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinKapt)
}

android {
    namespace = "com.mobilebreakero.details"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.accompanist.navigationAnimation)

    implementation(project(mapOf("path" to ":core:data")))
    implementation(project(mapOf("path" to ":core:domain")))
    implementation(project(mapOf("path" to ":common-ui")))

    // google
    implementation(libs.google.services)
    // hilt

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.coil.compose)
    implementation(libs.compose.ui.util)
    implementation(libs.navigation.compose)
    implementation(libs.compose.material)
    implementation(libs.retrofit)
    implementation(libs.google.gson)
    implementation(libs.retrofit.gson)
    implementation(libs.coroutines)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.android.compiler)
    implementation(libs.paging.compose)
    implementation(libs.paging)
    implementation(libs.hilt.navigation.compose)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)

    implementation(libs.firebase.storage)

}