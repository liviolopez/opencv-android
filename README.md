# OpenCV Android Samples

- Official Release Website
  https://opencv.org/releases/

- GitHub OpenCV account
  https://github.com/opencv

- GitHub OpenCV repository
  https://github.com/opencv/opencv

- Compile OpenCV project - All platforms
  https://github.com/opencv/opencv/issues/21389

---

### OpenCV compilation
Before compiling the project, you need to create a directory in which the results will be added, it is recommended that this directory is not within the same opencv project

```bash
$ ls -la
opencv-project-4.7

$ mkdir opencv-build
$ ls -la
opencv-project-4.7
opencv-build

$ cd opencv-build

$ cmake -DBUILD_ZLIB=OFF -DCMAKE_SYSTEM_PROCESSOR=arm64 -DCMAKE_OSX_ARCHITECTURES=arm64 -DCMAKE_BUILD_TYPE=Release -DBUILD_EXAMPLES=ON -DBUILD_TESTS=OFF -DBUILD_PERF_TESTS=OFF  -DCMAKE_INSTALL_PREFIX=/usr/local/opencv  -DBUILD_opencv_python3=ON -DPYTHON_DEFAULT_EXECUTABLE=/opt/anaconda3/envs/onnx/bin/python ../opencv-project-4.7

$ make -j8
$ sudo make install
```

---

### OpenCV and Android Studio
https://www.geeksforgeeks.org/how-to-add-opencv-library-into-android-application-using-android-studio/

#### Add OpenCV directly to your Gradle configuration
You do not need to copy the OpenCV project into the android project that will use it, it is preferable to keep it separate or create a repository with it.
To include it you must do the following:

Add OpenCV directly to your Gradle configuration into settings.gradle.kts (kotlin) / settings.gradle (groovy)
```kotlin
rootProject.name = "My Application"
include(":app")

// .........

include(":opencv")
project(":opencv").projectDir = File("../../opencv-android-sdk/sdk")
```

And into app/build.gradle.kts or the module that will use opencv
```kotlin
android {
    // ......
    
    defaultConfig {
        // ......

        externalNativeBuild {
            cmake {
                cppFlags.add("-std=c++11 -frtti -fexceptions")
                arguments.add("-DOpenCV_DIR=${project(":opencv").rootDir}/sdk/native/jni")
            }
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
}
```

#### Initialize/Load OpenCV when launching the app
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initOpenCV()
    }

    private fun initOpenCV() {
        System.loadLibrary("opencv_java4")
        if (!OpenCVLoader.initDebug()) println("GG")
    }
}

```
