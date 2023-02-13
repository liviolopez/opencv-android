package ai.caper.opencv.samples.opencv

import ai.caper.opencv.samples.ui.colorsensor.SensorColorInfo
import android.graphics.Bitmap
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Rect
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
        val roi = Rect(mat.cols() / 4, mat.rows() / 4, mat.cols() / 2, mat.rows() / 2)
        val roiImage = Mat(mat, roi)
        val scalarInfo = Core.mean(roiImage)

        return SensorColorInfo(bitmap, brightnessLevel, scalarInfo)
    }
}
