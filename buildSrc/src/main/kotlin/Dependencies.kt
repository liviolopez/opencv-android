object Build {
    const val compileSdk = 33
    const val minSdk = 26
    const val targetSdk = 33
    const val toolsVersion = "33.0.0"
    const val ndkVersion = "24.0.8215888"
}

private object Versions {
    const val gradle = "7.4.1"
    const val kotlin = "1.8.10"
    const val hilt = "2.45"
    const val navigation = "2.4.2"
    const val okhttp3 = "4.10.0"
}

object Plugins {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val dagger_hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
}

object Dep {
    const val CoreKtx = "androidx.core:core-ktx:1.9.0"
    const val AppCompat = "androidx.appcompat:appcompat:1.6.1"
    const val Material = "com.google.android.material:material:1.8.0"
    const val ConstraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"

    const val GoogleMLKit = "com.google.mlkit:barcode-scanning:17.0.3"
    const val DatastorePreferences = "androidx.datastore:datastore-preferences:1.1.0-dev01"
    const val Okhttp3 = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"

    object Kotlin {
        const val StdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val Reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
        const val Coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    }

    object Navigation {
        private const val Version = "2.5.3"
        const val Fragment = "androidx.navigation:navigation-fragment-ktx:${Version}"
        const val UI = "androidx.navigation:navigation-ui-ktx:${Version}"
    }

    object Retrofit2 {
        private const val Version = "2.9.0"
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Version}"
        const val Gson = "com.squareup.retrofit2:converter-gson:${Version}"
    }

    object Lifecycle {
        private const val Version = "2.5.1"
        const val ViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version}"
        const val LiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Version}"
        const val SavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Version}"
    }

    object Hilt {
        const val Android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val Compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

    object Room {
        private const val Version = "2.5.0"
        const val Runtime = "androidx.room:room-runtime:${Version}"
        const val Compiler = "androidx.room:room-compiler:${Version}"
        const val Ktx = "androidx.room:room-ktx:${Version}"
    }

    object Glide {
        private const val Version = "4.14.2"
        const val Glide = "com.github.bumptech.glide:glide:${Version}"
        const val Compiler = "com.github.bumptech.glide:compiler:${Version}"
    }

    object Camera {
        private const val Version = "1.2.1"
        const val Camera2 = "androidx.camera:camera-camera2:${Version}"
        const val Lifecycle = "androidx.camera:camera-lifecycle:${Version}"
        const val View = "androidx.camera:camera-view:${Version}"
    }

    // -----------------------------------------------------------------
    // ------------------------------TEST ------------------------------
    // -----------------------------------------------------------------

    object Test {
        const val CoreKtx = "androidx.test:core-ktx:1.3.0"
        const val Okhttp3 = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp3}"
        const val Fragment = "androidx.fragment:fragment-testing:1.5.5"

        object JUnit {
            const val JUnit = "junit:junit:4.13.2"
            const val Ext = "androidx.test.ext:junit:1.1.2"
        }

        object Espresso {
            private const val Version = "3.3.0"
            const val Core = "androidx.test.espresso:espresso-core:${Version}"
            const val Contrib = "androidx.test.espresso:espresso-contrib:${Version}"
        }

        object Hilt {
            const val Testing = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
            const val Compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        }
    }
}
