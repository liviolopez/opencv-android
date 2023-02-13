rootProject.name = "OpenCV Samples"
include(":app")

include(":opencv")
project(":opencv").projectDir = File("../../../../opencv-android-sdk/sdk")
