@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidlibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.firebase)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.mobilebreakero.navigation"
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
    implementation(project(mapOf("path" to ":core:domain")))
    implementation(project(mapOf("path" to ":ui:home")))
    implementation(project(mapOf("path" to ":ui:profile")))
    implementation(project(mapOf("path" to ":ui:scan")))
    implementation(project(mapOf("path" to ":ui:trips")))
    implementation(project(mapOf("path" to ":ui:addPost")))
    implementation(project(mapOf("path" to ":ui:details")))
    implementation(project(mapOf("path" to ":ui:auth")))
    implementation(project(mapOf("path" to ":ui:search")))
    implementation(project(mapOf("path" to ":ui:Interestedplaces")))
    implementation(project(mapOf("path" to ":ui:welcome")))
    implementation(project(mapOf("path" to ":core:data")))
    implementation(project(mapOf("path" to ":common-ui")))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.accompanist.navigationAnimation)

    // navigation
    implementation(libs.navigation.compose)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)


}