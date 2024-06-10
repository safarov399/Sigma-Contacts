package me.safarov399.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("contacts")
data class ContactEntity(

    @PrimaryKey(true) @ColumnInfo val id: Long = 0,
    @ColumnInfo val contactsId: String,
    @ColumnInfo val name: String,
    @ColumnInfo val number: String,
    @ColumnInfo val email: String? = null,
    @ColumnInfo val profilePhoto: String? = null
)
