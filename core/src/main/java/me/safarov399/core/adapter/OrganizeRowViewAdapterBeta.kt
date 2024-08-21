package me.safarov399.core.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.safarov399.core.pojo.OrganizeListItem

class OrganizeRowViewAdapterBeta : ListAdapter<OrganizeListItem, RecyclerView.ViewHolder>(OrganizeRowDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> SingleRowViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    me.safarov399.common.R.layout.one_image_one_text_view, parent, false
                )
            )

            VIEW_TYPE_TEXT -> DoubleRowViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    me.safarov399.common.R.layout.one_image_two_text_view, parent, false
                )
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (item) {
            is OrganizeListItem.OrganizeRowSingleLineModel -> (holder as SingleRowViewHolder).bind(
                item
            )

            is OrganizeListItem.OrganizeRowDoubleLineModel -> (holder as DoubleRowViewHolder).bind(
                item
            )
        }
    }

    inner class SingleRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon = itemView.findViewById<ImageView>(me.safarov399.common.R.id.one_image_one_text_view_iv)
        private val title = itemView.findViewById<TextView>(me.safarov399.common.R.id.one_image_one_text_view_tv)

        fun bind(item: OrganizeListItem.OrganizeRowSingleLineModel) {
            icon.setImageResource(item.icon)
            title.text = item.title
        }
    }

    inner class DoubleRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon = itemView.findViewById<ImageView>(me.safarov399.common.R.id.one_image_two_text_view_iv)
        private val title = itemView.findViewById<TextView>(me.safarov399.common.R.id.one_image_two_text_view_title_tv)
        private val description = itemView.findViewById<TextView>(me.safarov399.common.R.id.one_image_two_text_view_subtitle_tv)

        fun bind(item: OrganizeListItem.OrganizeRowDoubleLineModel) {
            icon.setImageResource(item.icon)
            title.text = item.title
            description.text = item.description
        }
    }


    private class OrganizeRowDiffCallBack : DiffUtil.ItemCallback<OrganizeListItem>() {

        override fun areItemsTheSame(
            oldItem: OrganizeListItem, newItem: OrganizeListItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: OrganizeListItem, newItem: OrganizeListItem
        ): Boolean {
            return when {
                oldItem.javaClass != newItem.javaClass -> false // Different types
                oldItem is OrganizeListItem.OrganizeRowSingleLineModel -> {
                    oldItem.icon == (newItem as OrganizeListItem.OrganizeRowSingleLineModel).icon && oldItem.title == newItem.title
                }

                oldItem is OrganizeListItem.OrganizeRowDoubleLineModel -> {
                    oldItem.icon == (newItem as OrganizeListItem.OrganizeRowDoubleLineModel).icon && oldItem.title == newItem.title && oldItem.description == newItem.description
                }

                else -> true
            }
        }


    }


    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_TEXT = 1
    }
}