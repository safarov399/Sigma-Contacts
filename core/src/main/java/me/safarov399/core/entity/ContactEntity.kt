package me.safarov399.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("contacts")
data class ContactEntity(

    @PrimaryKey(true) @ColumnInfo val id: Long,
    @ColumnInfo val name: String,
    @ColumnInfo val numberList: HashSet<String>,
    @ColumnInfo val emailList: HashSet<String>,
    @ColumnInfo val profilePhoto: String? = null
)
