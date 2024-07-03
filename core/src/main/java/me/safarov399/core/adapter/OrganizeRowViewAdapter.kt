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
import me.safarov399.core.pojo.OrganizeRowModel

class OrganizeRowViewAdapter: ListAdapter<OrganizeRowModel, OrganizeRowViewAdapter.OrganizeRowViewHolder>(OrganizeRowDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrganizeRowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(me.safarov399.common.R.layout.organize_row_view, parent, false)
        return OrganizeRowViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: OrganizeRowViewHolder, position: Int) {
        val organizeRowModel = getItem(position)
        holder.bind(organizeRowModel)
    }

    class OrganizeRowViewHolder(itemView: View, val ctx: Context): RecyclerView.ViewHolder(itemView) {
        private val icon = itemView.findViewById<ImageView>(me.safarov399.common.R.id.organize_row_view_iv)
        private val title = itemView.findViewById<TextView>(me.safarov399.common.R.id.organize_row_view_title_tv)
        private val description = itemView.findViewById<TextView>(me.safarov399.common.R.id.organize_row_view_description_tv)

        @SuppressLint("DiscouragedApi")
        fun bind(organizeRowModel: OrganizeRowModel) {
            icon.setImageResource(
                ctx.resources.getIdentifier(
                    organizeRowModel.icon, "drawable", ctx.packageName
                )
            )
            title.text = organizeRowModel.title
            description.text = organizeRowModel.description
        }
    }

    class OrganizeRowDiffCallBack: DiffUtil.ItemCallback<OrganizeRowModel>() {

        override fun areItemsTheSame(
            oldItem: OrganizeRowModel,
            newItem: OrganizeRowModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: OrganizeRowModel,
            newItem: OrganizeRowModel
        ): Boolean {
            return (oldItem.icon == newItem.icon) && (oldItem.title == newItem.title) && (oldItem.description == newItem.description)
        }
    }
}