package ai.caper.opencv.samples.opencv

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

object ImageUtils {

    fun generateBitmapFromDrawable(context: Context, drawableId: Int): Bitmap =
        with(ContextCompat.getDrawable(context, drawableId) as Drawable) {
            Bitmap.createBitmap(
                intrinsicWidth,
                intrinsicHeight,
                Bitmap.Config.ARGB_8888
            ).let { bitmap ->
                val canvas = Canvas(bitmap)

                setBounds(0, 0, canvas.width, canvas.height)
                draw(canvas)

                bitmap
            }
        }
}
