package me.safarov399.core.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import me.safarov399.core.entity.ContactEntity
import me.safarov399.core.pojo.ContactColors

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

        private val profilePhotoLetterTv: TextView =
            itemView.findViewById(me.safarov399.common.R.id.profile_photo_letter_tv)

        @SuppressLint("DiscouragedApi")
        fun bind(contactEntity: ContactEntity) {
            contactNameTv.text = contactEntity.firstName
            if (contactEntity.profilePhoto.isNotBlank()) {
                profilePhotoIv.setImageResource(
                    ctx.resources.getIdentifier(
                        contactEntity.profilePhoto, "drawable", ctx.packageName
                    )
                )
            } else {
                val firstLetter = contactEntity.firstName.first().toString()
                if (firstLetter.matches(Regex("[a-zA-Z\u0400-\u04FF\u011E\u011F\u0130\u0131\u00F6\u00FC\u015E\u015F\u00E7\u0259\u00C7\u018F\u00D6\u00DC]"))) {
                    profilePhotoLetterTv.text = firstLetter.uppercase()
                    profilePhotoIv.setImageResource(0)
                } else {
                    profilePhotoIv.setImageResource(
                        ctx.resources.getIdentifier(
                            "profile_people_account", "drawable", ctx.packageName
                        )
                    )
                    profilePhotoLetterTv.text = ""
                }
            }
            if (contactEntity.color != null) {
                val drawable = GradientDrawable()
                drawable.shape = GradientDrawable.OVAL
                drawable.setColor(contactEntity.color)
                profilePhotoIv.background = drawable
            }
            if (contactEntity.color == null) {
                val drawable = GradientDrawable()
                drawable.shape = GradientDrawable.OVAL
                drawable.setColor(ContactColors.COLORS.random())
                profilePhotoIv.background = drawable
            }


        }
    }

    class ContactViewDiffCallback : DiffUtil.ItemCallback<ContactEntity>() {

        override fun areItemsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ContactEntity, newItem: ContactEntity
        ): Boolean {
            return (oldItem.firstName == newItem.firstName)
        }
    }
}