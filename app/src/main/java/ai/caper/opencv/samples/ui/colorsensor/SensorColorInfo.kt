package ai.caper.opencv.samples.ui.colorsensor

import android.graphics.Bitmap
import android.graphics.Color
import java.util.UUID

data class SensorColorInfo(
    val id: String = UUID.randomUUID().toString(),
    val bitmap: Bitmap,
    val brightnessLevel: Int,
    val predominantColor: Color
)
