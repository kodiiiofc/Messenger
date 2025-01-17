import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
//    kotlin("kapt")

    id("org.jetbrains.kotlin.plugin.serialization")

//    id("kotlin-kapt")
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.kodiiiofc.urbanuniversity.jetpackcompose.messenger"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kodiiiofc.urbanuniversity.jetpackcompose.messenger"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // set value part
        buildConfigField("String",
            "SUPABASE_ANON_KEY",
            "\"${gradleLocalProperties(rootDir, providers)["SUPABASE_ANON_KEY"]}\"")

        buildConfigField("String",
            "SUPABASE_URL",
            "\"${gradleLocalProperties(rootDir, providers)["SUPABASE_URL"]}\"")
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
        buildConfig = true
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
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // supabase
//    implementation("io.github.jan-tennert.supabase:postgrest-kt:1.0.0")
//    implementation("io.github.jan-tennert.supabase:storage-kt:1.0.0")
//    implementation("io.github.jan-tennert.supabase:auth-kt:1.0.0")
    implementation(libs.supabase.kt)
//    implementation(libs.supabase.postgrest)
    implementation(libs.supabase.storage)
    implementation(libs.supabase.realtime)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.utils)
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:2.0.0")

    //dagger hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    //navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    //Coil
    implementation(libs.coil.kt.coil.compose)

}