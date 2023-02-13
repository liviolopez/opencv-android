package ai.caper.opencv.samples.ui.colorsensor

import android.graphics.Bitmap
import android.graphics.Color

data class SensorColorInfo(
    val bitmap: Bitmap,
    val brightnessLevel: Int,
    val predominantColor: Color
)
