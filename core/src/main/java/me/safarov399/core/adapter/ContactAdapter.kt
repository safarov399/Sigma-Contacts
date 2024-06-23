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
import me.safarov399.core.entity.ContactEntity

class ContactAdapter :
    ListAdapter<ContactEntity, ContactAdapter.ContactViewHolder>(ContactViewDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(me.safarov399.common.R.layout.contact_view, parent, false)
        return ContactViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }

    class ContactViewHolder(itemView: View, private val ctx: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val contactNameTv: TextView =
            itemView.findViewById(me.safarov399.common.R.id.contact_name)
        private val profilePhotoIv: ImageView =
            itemView.findViewById(me.safarov399.common.R.id.profile_photo)

        @SuppressLint("DiscouragedApi")
        fun bind(contactEntity: ContactEntity) {
            contactNameTv.text = contactEntity.firstName
            if(contactEntity.profilePhoto != null) {
                profilePhotoIv.setImageResource(
                    ctx.resources.getIdentifier(
                        contactEntity.profilePhoto, "drawable", ctx.packageName
                    )
                )
            }
            else {
                profilePhotoIv.setImageResource(
                    ctx.resources.getIdentifier(
                        "profile_people_account", "drawable", ctx.packageName
                    )
                )
            }
        }
    }

    class ContactViewDiffCallback : DiffUtil.ItemCallback<ContactEntity>() {

        override fun areItemsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContactEntity,
            newItem: ContactEntity
        ): Boolean {
            return (oldItem.firstName == newItem.firstName)
        }
    }
}