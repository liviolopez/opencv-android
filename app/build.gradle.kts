plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "ai.caper.opencv.samples"

    signingConfigs {
        register("release") {
            storeFile = file("../cert/cert.keystore") // <- this information must be in a secure storage (CI/CD)
            keyAlias = "alias-test" // <- Must be in a secure storage (CI/CD)
            keyPassword = "123abc" // <- this information must be in a secure storage (CI/CD)
            storePassword = "123abc" // <- this information must be in a secure storage (CI/CD)
        }
    }

    compileSdk = Build.compileSdk
    buildToolsVersion = Build.toolsVersion
    ndkVersion = Build.ndkVersion

    defaultConfig {
        applicationId = "ai.caper.opencv.samples"

        minSdk = Build.minSdk
        targetSdk = Build.targetSdk

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "ai.caper.opencv.samples.AppTestRunner"

        externalNativeBuild {
            cmake {
                cppFlags.add("-std=c++11 -frtti -fexceptions")
                arguments.add("-DOpenCV_DIR=${project(":opencv").rootDir}/sdk/native/jni")
            }
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        named("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }
    }

    splits {
        abi {
            reset()
            isEnable = true
            isUniversalApk = false
            include("x86", "arm64-v8a", "armeabi-v7a")
        }
    }

    lint {
        abortOnError = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":opencv"))

    implementation(Dep.Kotlin.StdLib)
    implementation(Dep.Kotlin.Coroutines)
    implementation(Dep.Kotlin.Reflect)

    implementation(Dep.CoreKtx)
    implementation(Dep.AppCompat)
    implementation(Dep.Material)
    implementation(Dep.ConstraintLayout)

    implementation(Dep.GoogleMLKit)
    implementation(Dep.DatastorePreferences)
    implementation(Dep.Okhttp3)

    implementation(Dep.Navigation.Fragment)
    implementation(Dep.Navigation.UI)

    implementation(Dep.Retrofit2.Retrofit)
    implementation(Dep.Retrofit2.Gson)

    implementation(Dep.Lifecycle.ViewModel)
    implementation(Dep.Lifecycle.LiveData)
    implementation(Dep.Lifecycle.SavedState)

    implementation(Dep.Hilt.Android)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    kapt(Dep.Hilt.Compiler)

    implementation(Dep.Room.Runtime)
    implementation(Dep.Room.Ktx)
    kapt(Dep.Room.Compiler)

    implementation(Dep.Glide.Glide)
    kapt(Dep.Glide.Compiler)

    implementation(Dep.Camera.Camera2)
    implementation(Dep.Camera.Lifecycle)
    implementation(Dep.Camera.View)

    // -----------------------------------------------------------------
    // ------------------------------TEST ------------------------------
    // -----------------------------------------------------------------

    testImplementation(Dep.Test.JUnit.JUnit)
    androidTestImplementation(Dep.Test.JUnit.JUnit)
    androidTestImplementation(Dep.Test.JUnit.Ext)

    androidTestImplementation(Dep.Test.Espresso.Core)
    androidTestImplementation(Dep.Test.Espresso.Contrib)

    androidTestImplementation(Dep.Test.CoreKtx)
    androidTestImplementation(Dep.Test.Okhttp3)
    debugImplementation(Dep.Test.Fragment)

    androidTestImplementation(Dep.Test.Hilt.Testing)
    kaptAndroidTest(Dep.Test.Hilt.Compiler)
}
