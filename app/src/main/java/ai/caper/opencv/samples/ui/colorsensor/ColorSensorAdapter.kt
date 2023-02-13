package ai.caper.opencv.samples.ui.colorsensor

import ai.caper.opencv.samples.databinding.ItemSensorColorInfoBinding
import ai.caper.opencv.samples.opencv.ColorUtils.toHexCodeColor
import ai.caper.opencv.samples.ui._components.BindingViewHolder
import ai.caper.opencv.samples.ui._components.typed
import ai.caper.opencv.samples.ui._components.viewHolderFrom
import ai.caper.opencv.samples.utils.extensions.setImage
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class ColorSensorAdapter : ListAdapter<SensorColorInfo, BindingViewHolder<*>>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return parent.viewHolderFrom(ItemSensorColorInfoBinding::inflate)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        val item = getItem(position)
        holder.typed<ItemSensorColorInfoBinding>().bind(item)
    }

    class ItemComparator : DiffUtil.ItemCallback<SensorColorInfo>() {

        override fun areItemsTheSame(oldItem: SensorColorInfo, newItem: SensorColorInfo) =
            oldItem.bitmap == newItem.bitmap

        override fun areContentsTheSame(oldItem: SensorColorInfo, newItem: SensorColorInfo) = oldItem == newItem
    }

    @SuppressLint("SetTextI18n")
    private fun BindingViewHolder<ItemSensorColorInfoBinding>.bind(sensorColorInfo: SensorColorInfo) {
        binding.apply {
            ivThumbnail.setImage(sensorColorInfo.bitmap)
            tvBrightnessLevel.text = sensorColorInfo.brightnessLevel.toString()

            val r = sensorColorInfo.predominantColor.red().toInt().toHexCodeColor()
            val g = sensorColorInfo.predominantColor.green().toInt().toHexCodeColor()
            val b = sensorColorInfo.predominantColor.blue().toInt().toHexCodeColor()

            tvPredominantColorInfo.text = "R:$r - G:$g - B:$b"
            tvPredominantColorPreview.background = ColorDrawable(Color.parseColor("#${r + g + b}"))
        }
    }
}
