plugins {
    alias(libs.plugins.android.application)
}
/* plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
} */

android {
    namespace = "com.myrhstudios.mobileamp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.myrhstudios.mobileamp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "0.1"
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

    buildFeatures {
        viewBinding = true
        // dataBinding = false
        /*
        There are two binding systems that look similar but are different beasts:
        View Binding
            Generates binding classes
            No <layout> tag
            No expressions
            Lightweight and fast
        Data Binding
            Uses <layout> root
            Supports expressions, variables, LiveData
            Heavier, more powerful
            Data Binding allows: LiveData, Binding expressions, ViewModel binding
         */
    }

}

kotlin {
    jvmToolchain(21)
}

dependencies {

    // Android core
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Media3 / ExoPlayer
    implementation("androidx.media3:media3-exoplayer:1.2.1")
    implementation("androidx.media3:media3-ui:1.2.1")

    // Networking
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")

    // JSON
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
}
