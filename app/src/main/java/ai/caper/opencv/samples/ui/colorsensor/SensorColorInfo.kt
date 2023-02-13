package ai.caper.opencv.samples.ui.colorsensor

import android.graphics.Bitmap
import org.opencv.core.Scalar

data class SensorColorInfo(val bitmap: Bitmap, val brightnessLevel: Int, val scalarInfo: Scalar)
