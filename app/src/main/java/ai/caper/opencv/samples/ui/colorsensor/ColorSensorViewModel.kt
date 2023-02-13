package ai.caper.opencv.samples.ui.colorsensor

import android.app.Application
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ColorSensorViewModel @Inject constructor(
    private val app: Application
) : ViewModel()
