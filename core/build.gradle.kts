plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("kotlin-parcelize")
  id("kotlin-android")
  id("kotlin-kapt")
  id("dagger.hilt.android.plugin")
  id("com.google.devtools.ksp") version "1.6.10-1.0.4"
  id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

android {
  compileSdk = ConfigData.compileSdk

  defaultConfig {
    minSdk = ConfigData.minSdk
    targetSdk = ConfigData.targetSdk

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {
  implementationPlatform(Dependencies.platformLibraries)
  implementation(Dependencies.appLibraries)
  kapt(Dependencies.kaptLibraries)
  ksp(Dependencies.kspLibraries)
  testImplementation(Dependencies.testLibraries)
  androidTestImplementation(Dependencies.androidTestLibraries)
  debugImplementation(Dependencies.debugLibraries)
}