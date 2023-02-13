package ai.caper.opencv.samples.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File

object FileSystemUtils {

    fun getAlbumStorageDir(context: Context): File {
        // Get the directory for the app's private pictures directory. 
        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "caper_ai")

        if (!file.mkdirs()) {
            Log.e("ERROR", "Directory not created")
        }

        return file
    }

    fun getAlbumStorageDir(): File? {
        // Get the directory for the user's public pictures directory. 
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "caper_ai")

        if (!file.mkdirs()) {
            Log.e("ERROR", "Directory not created")
        }

        return file
    }
}
