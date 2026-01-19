plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.devtools.ksp") version "2.0.21-1.0.25"

}

android {

    namespace = "gz.dam.simondice"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "gz.dam.simondice"
        minSdk = 30
        targetSdk = 36
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
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            // Opción A: Excluir los archivos que dan problemas
            excludes += "/META-INF/native-image/org.mongodb/bson/native-image.properties"
        }
    }

    dependencies {
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.compose.ui)
        implementation(libs.androidx.compose.ui.graphics)
        implementation(libs.androidx.compose.ui.tooling.preview)
        implementation(libs.androidx.compose.material3)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.compose.ui.test.junit4)
        debugImplementation(libs.androidx.compose.ui.tooling)
        debugImplementation(libs.androidx.compose.ui.test.manifest)

        val room_version = "2.7.0-alpha11"

        implementation("androidx.room:room-runtime:$room_version")
        implementation("androidx.room:room-ktx:$room_version")

        ksp("androidx.room:room-compiler:$room_version")

        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
        implementation("androidx.activity:activity-compose:1.9.3")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

        implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.0.0")

        implementation("org.mongodb:bson-kotlin:5.0.0")
        // MongoDB Driver
        implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.0.0")

        // Coroutines (ya deberías tenerla)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

        // Timber para logging (opcional pero recomendado)
        implementation("com.jakewharton.timber:timber:5.0.1")

        coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.4")

        implementation("org.mongodb:mongodb-driver-sync:4.11.1")
        implementation("org.mongodb:bson:4.11.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    }
}