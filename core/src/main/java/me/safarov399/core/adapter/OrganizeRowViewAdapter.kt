package me.safarov399.core.adapter

import android.util.TypedValue
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
        private val icon = itemView.findViewById<ImageView>(me.safarov399.common.R.id.one_image_one_text_view_iv)
        private val title = itemView.findViewById<TextView>(me.safarov399.common.R.id.one_image_one_text_view_tv)

        fun bind(item: OrganizeListItem.OrganizeRowSingleLineModel) {
            icon.setImageResource(item.icon)
            title.text = item.title
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        }
    }

    inner class DoubleRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon = itemView.findViewById<ImageView>(me.safarov399.common.R.id.one_image_two_text_view_iv)
        private val title = itemView.findViewById<TextView>(me.safarov399.common.R.id.one_image_two_text_view_title_tv)
        private val subTitle = itemView.findViewById<TextView>(me.safarov399.common.R.id.one_image_two_text_view_subtitle_tv)

        fun bind(item: OrganizeListItem.OrganizeRowDoubleLineModel) {
            icon.setImageResource(item.icon)
            title.text = item.title
            subTitle.text = item.description
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
                    me.safarov399.common.R.layout.one_image_one_text_view, parent, false
                )
            )

            1 -> DoubleRowViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    me.safarov399.common.R.layout.one_image_two_text_view, parent, false
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