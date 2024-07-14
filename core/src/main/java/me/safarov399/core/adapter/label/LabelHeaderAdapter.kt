package me.safarov399.core.adapter.label

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.safarov399.core.pojo.LabelHeaderItem

class LabelHeaderAdapter: ListAdapter<LabelHeaderItem, LabelHeaderAdapter.LabelHeaderViewHolder>(LabelHeaderDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelHeaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(me.safarov399.common.R.layout.label_header_view, parent, false)
        return LabelHeaderViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabelHeaderViewHolder, position: Int) {
        val view = getItem(position)
        return holder.bind(view)
    }

    inner class LabelHeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val labelHeaderImg: ImageView = itemView.findViewById(me.safarov399.common.R.id.label_header_iv)

        private val labelHeaderTv: TextView = itemView.findViewById(me.safarov399.common.R.id.label_header_tv)
        fun bind(labelHeaderItem: LabelHeaderItem) {
            labelHeaderImg.setImageResource(labelHeaderItem.img)
            labelHeaderTv.text = labelHeaderItem.label
        }
    }

    class LabelHeaderDiffCallback: DiffUtil.ItemCallback<LabelHeaderItem>() {

        override fun areItemsTheSame(oldItem: LabelHeaderItem, newItem: LabelHeaderItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: LabelHeaderItem, newItem: LabelHeaderItem): Boolean {
            return oldItem.label == newItem.label && oldItem.img == newItem.img
        }
    }

}