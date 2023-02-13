package ai.caper.opencv.samples.opencv

object ColorUtils {

    fun Int.toHexCodeColor() = Integer.toHexString(this).padStart(2, '0').uppercase()
    
}
