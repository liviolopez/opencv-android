package ai.caper.opencv.samples.ui.colorsensor

import ai.caper.opencv.samples.R
import ai.caper.opencv.samples.databinding.FragmentColorSensorBinding
import ai.caper.opencv.samples.opencv.ImageUtils.generateBitmapFromDrawable
import ai.caper.opencv.samples.opencv.OpenCvUtils
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
            R.drawable.nyc_8
        ).map { drawableId ->
            val bitmap = generateBitmapFromDrawable(requireContext(), drawableId)
            OpenCvUtils.analyzePhoto(bitmap)
        }.toMutableList()

        colorSensorAdapter.submitList(sensorInfoList)
    }
}
