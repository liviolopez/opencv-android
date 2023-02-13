package ai.caper.opencv.samples.opencv

import ai.caper.opencv.samples.ui.colorsensor.SensorColorInfo
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
import org.opencv.core.Point
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.core.Size
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

        val color = Scalar(0.0, 0.0, 255.0)
        val thickness = 6

        val size = Size()
        size.width = targetRect.width.toDouble()
        size.height = targetRect.height.toDouble()

        val center = Point()
        center.y = mat.rows() / 2.0
        center.x = mat.cols() / 2.0

        Imgproc.rectangle(mat, targetRect, color, thickness, Imgproc.LINE_8)

        val resultBitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mat, resultBitmap)

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Default) { delay(2000) }
        }

        val rgb = Scalar(scalarInfo.`val`[2],scalarInfo.`val`[1],scalarInfo.`val`[0])
        val red = rgb.`val`[0].toFloat()
        val green = rgb.`val`[1].toFloat()
        val blue = rgb.`val`[2].toFloat()
        val rgbColor = Color.valueOf(red, green, blue)

        return SensorColorInfo(resultBitmap, brightnessLevel, rgbColor)
    }
}
