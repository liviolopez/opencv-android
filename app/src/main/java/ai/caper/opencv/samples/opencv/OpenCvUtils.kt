package ai.caper.opencv.samples.opencv

import ai.caper.opencv.samples.ui.colorsensor.SensorColorInfo
import ai.caper.opencv.samples.utils.FileSystemUtils.getAlbumStorageDir
import android.graphics.Bitmap
import android.graphics.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import kotlin.math.roundToInt

object OpenCvUtils {

    fun analyzePhoto(bitmap: Bitmap): SensorColorInfo {
        // Convert the Bitmap to a Mat object
        val mat = Mat()
        Utils.bitmapToMat(bitmap, mat)

        // Calculate the average brightness of the image
        val grayImage = Mat()
        Imgproc.cvtColor(mat, grayImage, Imgproc.COLOR_BGR2GRAY)
        val avgBrightness = Core.mean(grayImage).`val`[0] / 255 * 100
        val brightnessLevel = avgBrightness.roundToInt()

        // Determine the predominant color in a specific area of the image
        val targetRect = Rect(mat.cols() / 4, mat.rows() / 4, mat.cols() / 2, mat.rows() / 2)
        val roiImage = Mat(mat, targetRect)
        val scalarInfo = Core.mean(roiImage)

        val colorScalar = Scalar(0.0, 0.0, 255.0)
        val thickness = 6

        val size = Size()
        size.width = targetRect.width.toDouble()
        size.height = targetRect.height.toDouble()

        val center = Point()
        center.y = mat.rows() / 2.0
        center.x = mat.cols() / 2.0

        Imgproc.rectangle(mat, targetRect, colorScalar, thickness, Imgproc.LINE_8)

        val resultBitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mat, resultBitmap)

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Default) { delay(2000) }
        }

        val rgb = Scalar(scalarInfo.`val`[2], scalarInfo.`val`[1], scalarInfo.`val`[0])
        val red = rgb.`val`[0].toFloat()
        val green = rgb.`val`[1].toFloat()
        val blue = rgb.`val`[2].toFloat()
        val rgbColor = Color.valueOf(red, green, blue)

        val doubleRectBitmap = identifyRedRect(bitmap)

        return SensorColorInfo(
            bitmap = doubleRectBitmap,
            brightnessLevel = brightnessLevel,
            predominantColor = rgbColor
        )
    }

    fun identifyLogo() {
        CoroutineScope(Dispatchers.IO).launch {
            val parentPath = getAlbumStorageDir()?.absolutePath
            println("OpenCvUtils: $parentPath")
            // Load the template of the logo to be detected
            val logo = Imgcodecs.imread("$parentPath/caper_logo.png")

            // Load the image to be processed
            val image = Imgcodecs.imread("$parentPath/background.png")
            // Create a Mat object to store the result of the matching
            val result = Mat()

            // Use the matchTemplate method to perform the template matching
            Imgproc.matchTemplate(image, logo, result, Imgproc.TM_CCOEFF_NORMED)

            // Normalize the result to the range [0, 1]
            Core.normalize(result, result, 0.0, 1.0, Core.NORM_MINMAX, -1, Mat())

            // Use the minMaxLoc method to get the position of the best match
            val minMaxLoc = Core.minMaxLoc(result)

            // Check if the match is above a certain threshold
            if (minMaxLoc.maxVal >= 0.8) {
                println("OpenCvUtils: Logo found at (${minMaxLoc.maxLoc.x}, ${minMaxLoc.maxLoc.y})")

                // Get the size of the template
                val height = logo.rows()
                val width = logo.cols()

                // Draw a rectangle around the logo
                val topLeft = Point(minMaxLoc.maxLoc.x, minMaxLoc.maxLoc.y)
                val bottomRight = Point(minMaxLoc.maxLoc.x + width, minMaxLoc.maxLoc.y + height)
                Imgproc.rectangle(image, topLeft, bottomRight, Scalar(0.0, 0.0, 255.0), 10)

                // Save the image with the rectangle
                Imgcodecs.imwrite("$parentPath/caper_logo_detection.jpg", image)
                println("OpenCvUtils: Logo caper_logo_detection saved")
            } else {
                println("OpenCvUtils: Logo not found")
            }
        }
    }

    fun identifyRedRect(bitmap: Bitmap): Bitmap {
        // Load the image into a Mat object
        val mat = Mat()
        Utils.bitmapToMat(bitmap, mat)

        // Convert the image to HSV color space
        val hsvMat = Mat()
        Imgproc.cvtColor(mat, hsvMat, Imgproc.COLOR_BGR2HSV)

        // Define the range of beige color in HSV color space
        val beigeLower = Scalar(10.0, 10.0, 160.0)
        val beigeUpper = Scalar(40.0, 40.0, 200.0)

        // Threshold the image to get the beige pixels
        val beigeMask = Mat()
        Core.inRange(hsvMat, beigeLower, beigeUpper, beigeMask)

        // Define the range of red color in HSV color space
        val redLower = Scalar(0.0, 70.0, 50.0)
        val redUpper = Scalar(10.0, 255.0, 255.0)

        // Threshold the image to get the red pixels
        val redMask = Mat()
        Core.inRange(hsvMat, redLower, redUpper, redMask)

        // Combine the beige and red masks to get the pixels that belong to the red rectangle on a beige surface
        val combinedMask = Mat()
        Core.bitwise_and(beigeMask, redMask, combinedMask)

        // Find the contours of the combined mask
        val contours = mutableListOf<MatOfPoint>()
        Imgproc.findContours(combinedMask, contours, Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE)

        // Find the largest contour
        var largestContour: MatOfPoint? = null
        var largestContourArea = 0.0
        for (contour in contours) {
            val contourArea = Imgproc.contourArea(contour)
            if (contourArea > largestContourArea) {
                largestContour = contour
                largestContourArea = contourArea
            }
        }

        if(largestContour != null) {
            // Find the bounding rectangle of the largest contour
            val boundingRect = Imgproc.boundingRect(largestContour)
            val rectToDraw = Rect(boundingRect.x, boundingRect.y, boundingRect.width, boundingRect.height)
            val colorRectLine = Scalar(255.0, 0.0, 255.0)
            val thickness = 6

            // Draw a rectangle with rounded corners on the image
            Imgproc.rectangle(mat, rectToDraw, colorRectLine, thickness, Imgproc.LINE_8)

            // Convert the Mat object back to a Bitmap
            val resultBitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888)
            Utils.matToBitmap(mat, resultBitmap)

            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Default) { delay(2500) }
            }

            return resultBitmap
        } else {
            return bitmap
        }
    }

//    // Not worked
//    fun colorMostFrequently(bitmap: Bitmap, rect: Rect) {
//        val mat = Mat()
//        Utils.bitmapToMat(bitmap, mat)
//
//        // Crop the rectangle from the image
//        val cropped = Mat(mat, rect)
//
//        // Convert the cropped image to HSV color space
//        val hsv = Mat()
//        Imgproc.cvtColor(cropped, hsv, Imgproc.COLOR_BGR2HSV)
//
//        // Create a histogram with 30 bins for each channel
//        val histSize = MatOfInt(30, 30, 30)
//        val ranges = MatOfFloat(0f, 180f, 0f, 256f, 0f, 256f)
//        val hist = Mat()
//        val channels = MatOfInt(0, 1, 2)
//        Imgproc.calcHist(listOf(hsv), channels, Mat(), hist, histSize, ranges, false)
//
//        // Find the bin with the highest value
//        val result = Core.minMaxLoc(hist)
//        val color = result.maxLoc
//
//        // Convert the bin indices back to the original hue, saturation and value values
//        val hue = (color.x * 180 / 30).toInt()
//        val saturation = (color.y * 256 / 30).toInt()
//        val value = (color * 256 / 30).toInt()
//
//        println("The most frequent color in the rectangle is: H: $hue, S: $saturation, V: $value")
//    }
}
