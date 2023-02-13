package ai.caper.opencv.samples.ui._components

import ai.caper.opencv.samples.ui.colorsensor.ColorSensorFragment
import ai.caper.opencv.samples.ui.home.HomeFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
class AppFragmentFactory @Inject constructor() : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            HomeFragment::class.java.name -> HomeFragment()
            ColorSensorFragment::class.java.name -> ColorSensorFragment()
            else -> super.instantiate(classLoader, className)
        }
    }
}
