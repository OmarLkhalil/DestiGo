
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidlibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.firebase)
}

android {
    namespace = "com.mobilebreakero.data"
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
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(project(mapOf("path" to ":core:domain")))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.storage)

    implementation(libs.google.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.android.compiler)

    //Javamail for Smtp
    implementation(libs.javaMail)
    implementation(libs.javaActivation)
}