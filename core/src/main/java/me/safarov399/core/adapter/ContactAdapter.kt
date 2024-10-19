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
import me.safarov399.core.utils.StringUtils

class ContactAdapter : ListAdapter<ContactEntity, ContactAdapter.ContactViewHolder>(ContactViewDiffCallback()) {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(me.safarov399.common.R.layout.contact_view, parent, false)
        return ContactViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(position, contact)
        }
    }
    fun setOnClickListener(listener: OnClickListener?) {
        this.onClickListener = listener
    }

    class ContactViewHolder(itemView: View, private val ctx: Context) :
        RecyclerView.ViewHolder(itemView) {

        private val contactNameTv: TextView = itemView.findViewById(me.safarov399.common.R.id.contact_name)

        private val profilePhotoIv: ImageView = itemView.findViewById(me.safarov399.common.R.id.profile_photo)

        private val profilePhotoLetterTv: TextView = itemView.findViewById(me.safarov399.common.R.id.profile_photo_letter_tv)

        @SuppressLint("DiscouragedApi")
        fun bind(contactEntity: ContactEntity) {
            val displayName = contactEntity.firstName.trim() + " " + contactEntity.lastName.trim()
            val isNameEmpty = displayName.isBlank()
            contactNameTv.text = if(!isNameEmpty) displayName else contactEntity.numbers[0]

            /**
             * If the profile photo attribute has other photo set as the profile photo, that photo will be set. Otherwise, if that field is blank, the default one(account photo) will be left as it is
             */
            if (contactEntity.profilePhoto.isNotBlank()) {
                profilePhotoIv.setImageResource(
                    ctx.resources.getIdentifier(
                        contactEntity.profilePhoto, "drawable", ctx.packageName
                    )
                )
            } else {
                val firstLetter = if(isNameEmpty) {
                    "."
                } else displayName[0].toString()

                if (firstLetter.matches(Regex(StringUtils.FIRST_LETTER_CHECKING_REGEX))) {
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