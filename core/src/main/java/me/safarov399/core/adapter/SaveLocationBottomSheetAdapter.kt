package me.safarov399.core.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.safarov399.core.entity.SaveLocationEntity

class SaveLocationBottomSheetAdapter :
    ListAdapter<SaveLocationEntity, SaveLocationBottomSheetAdapter.SaveLocationViewHolder>(
        SaveLocationViewDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveLocationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(me.safarov399.common.R.layout.save_location, parent, false)
        return SaveLocationViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: SaveLocationViewHolder, position: Int) {
        val saveLocationItem = getItem(position)
        holder.bind(saveLocationItem)
    }

    class SaveLocationViewHolder(itemView: View, private val ctx: Context) : RecyclerView.ViewHolder(itemView) {

        private val saveLocationTitle: TextView = itemView.findViewById(me.safarov399.common.R.id.save_location_tv)
        private val saveLocationLogo: ImageView = itemView.findViewById(me.safarov399.common.R.id.save_location_iv)
//        private val saveLocationNumber: TextView

        @SuppressLint("DiscouragedApi")
        fun bind(saveLocationEntity: SaveLocationEntity) {
            saveLocationTitle.text = saveLocationEntity.title
            saveLocationLogo.setImageResource(
                ctx.resources.getIdentifier(
                    saveLocationEntity.logo, "drawable", ctx.packageName
                )
            )
        }
    }


    class SaveLocationViewDiffCallback : DiffUtil.ItemCallback<SaveLocationEntity>() {

        override fun areItemsTheSame(
            oldItem: SaveLocationEntity,
            newItem: SaveLocationEntity
        ): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(
            oldItem: SaveLocationEntity,
            newItem: SaveLocationEntity
        ): Boolean {
            return (oldItem.logo == newItem.logo) && (oldItem.title == newItem.title)
        }
    }
}