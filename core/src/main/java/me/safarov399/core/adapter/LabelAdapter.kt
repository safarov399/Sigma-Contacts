package me.safarov399.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.safarov399.core.entity.LabelEntity

class LabelAdapter : ListAdapter<LabelEntity, LabelAdapter.LabelViewHolder>(LabelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(me.safarov399.common.R.layout.label_view, parent, false)
        return LabelViewHolder(view)
    }

    override fun onBindViewHolder(holder: LabelViewHolder, position: Int) {
        val label = getItem(position)
        return holder.bind(label)
    }

    inner class LabelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val labelNameTv: TextView = itemView.findViewById(me.safarov399.common.R.id.label_view_name_tv)
        private val labelCount: TextView = itemView.findViewById(me.safarov399.common.R.id.label_view_number_tv)

        fun bind(labelEntity: LabelEntity) {
            labelNameTv.text = labelEntity.name
            labelCount.text = labelEntity.numberOfContacts.toString()
        }
    }

    class LabelDiffCallback : DiffUtil.ItemCallback<LabelEntity>() {

        override fun areItemsTheSame(oldItem: LabelEntity, newItem: LabelEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LabelEntity, newItem: LabelEntity): Boolean {
            return oldItem.name == newItem.name && oldItem.numberOfContacts == newItem.numberOfContacts
        }
    }
}