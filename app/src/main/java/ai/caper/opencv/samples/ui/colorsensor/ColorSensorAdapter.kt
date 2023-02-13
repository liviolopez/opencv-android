package ai.caper.opencv.samples.ui.colorsensor

import ai.caper.opencv.samples.databinding.ItemSensorColorInfoBinding
import ai.caper.opencv.samples.ui._components.BindingViewHolder
import ai.caper.opencv.samples.ui._components.typed
import ai.caper.opencv.samples.ui._components.viewHolderFrom
import ai.caper.opencv.samples.utils.extensions.setImage
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

    private fun BindingViewHolder<ItemSensorColorInfoBinding>.bind(sensorColorInfo: SensorColorInfo) {
        binding.apply {
            ivThumbnail.setImage(sensorColorInfo.bitmap)
            tvBrightnessLevel.text = sensorColorInfo.brightnessLevel.toString()
            tvScalarInfo.text = sensorColorInfo.scalarInfo.toString()
        }
    }
}
