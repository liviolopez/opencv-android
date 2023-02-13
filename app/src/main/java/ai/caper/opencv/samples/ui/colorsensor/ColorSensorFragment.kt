package ai.caper.opencv.samples.ui.colorsensor

import ai.caper.opencv.samples.R
import ai.caper.opencv.samples.databinding.FragmentColorSensorBinding
import ai.caper.opencv.samples.opencv.ImageUtils.generateBitmapFromDrawable
import ai.caper.opencv.samples.opencv.OpenCvUtils
import ai.caper.opencv.samples.opencv.OpenCvUtils.identifyLogo
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@AndroidEntryPoint
class ColorSensorFragment : Fragment(R.layout.fragment_color_sensor) {

    private val viewModel: ColorSensorViewModel by activityViewModels()

    private lateinit var binding: FragmentColorSensorBinding
    private var colorSensorAdapter = ColorSensorAdapter()

    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentColorSensorBinding.bind(view)
        binding.rvSensorColorInfoList.adapter = colorSensorAdapter

        analyseLocalImages()
        identifyLogo()
    }

    private fun analyseLocalImages() {
        val sensorInfoList = listOf(
            R.drawable.nyc_1,
            R.drawable.nyc_2,
            R.drawable.nyc_3,
            R.drawable.nyc_4,
            R.drawable.nyc_5,
            R.drawable.nyc_6,
            R.drawable.nyc_7,
            R.drawable.nyc_8,
            R.drawable.nyc_brightness_0,
            R.drawable.nyc_brightness_25,
            R.drawable.nyc_brightness_50,
            R.drawable.nyc_brightness_75,
            R.drawable.nyc_brightness_100,
            R.drawable.rect_1,
            R.drawable.rect_2,
            R.drawable.rect_3,
            R.drawable.rect_4,
            R.drawable.rect_5
        ).map { drawableId ->
            val bitmap = generateBitmapFromDrawable(requireContext(), drawableId)
            OpenCvUtils.analyzePhoto(bitmap)
        }.toMutableList()

        colorSensorAdapter.submitList(sensorInfoList)
    }
}
