package me.safarov399.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.safarov399.core.pojo.OrganizeListItem

class OrganizeRowViewAdapter(
    private val items: List<OrganizeListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class SingleRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon = itemView.findViewById<ImageView>(me.safarov399.common.R.id.organize_row_view_iv)
        private val title = itemView.findViewById<TextView>(me.safarov399.common.R.id.organize_row_view_title_tv)

        fun bind(item: OrganizeListItem.OrganizeRowSingleLineModel) {
            icon.setImageResource(item.icon)
            title.text = item.title
        }
    }

    inner class DoubleRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon = itemView.findViewById<ImageView>(me.safarov399.common.R.id.organize_row_view_iv)
        private val title = itemView.findViewById<TextView>(me.safarov399.common.R.id.organize_row_view_title_tv)
        private val description = itemView.findViewById<TextView>(me.safarov399.common.R.id.organize_row_view_description_tv)

        fun bind(item: OrganizeListItem.OrganizeRowDoubleLineModel) {
            icon.setImageResource(item.icon)
            title.text = item.title
            description.text = item.description
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is OrganizeListItem.OrganizeRowSingleLineModel -> 0
            is OrganizeListItem.OrganizeRowDoubleLineModel -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> SingleRowViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    me.safarov399.common.R.layout.organize_row_single_line_view, parent, false
                )
            )

            1 -> DoubleRowViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    me.safarov399.common.R.layout.organize_row_double_line_view, parent, false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is OrganizeListItem.OrganizeRowSingleLineModel -> (holder as SingleRowViewHolder).bind(item)
            is OrganizeListItem.OrganizeRowDoubleLineModel -> (holder as DoubleRowViewHolder).bind(item)
        }
    }

    override fun getItemCount() = items.size
}