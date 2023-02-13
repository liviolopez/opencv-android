package ai.caper.opencv.samples.ui.home

import ai.caper.opencv.samples.R
import ai.caper.opencv.samples.databinding.FragmentHomeBinding
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.FlowPreview

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = NavHostFragment.findNavController(this)
    }
    
    @FlowPreview
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        
        binding.btCalculateBrightnessAndColor.setOnClickListener {
            navController.navigate(
                HomeFragmentDirections.actionHomeFragmentToColorSensorFragment()
            )
        }
    }
}
