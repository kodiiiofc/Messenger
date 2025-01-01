import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.kodiiiofc.urbanuniversity.jetpackcompose.messenger"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kodiiiofc.urbanuniversity.jetpackcompose.messenger"
        minSdk = 24
        targetSdk = 34
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
    implementation("io.github.jan-tennert.supabase:supabase-kt:1.0.0")
    implementation("io.ktor:ktor-client-okhttp:2.0.0")
    implementation("io.ktor:ktor-client-android:2.0.0")
    implementation("io.ktor:ktor-client-core:2.0.0")
    implementation("io.ktor:ktor-utils:2.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

}