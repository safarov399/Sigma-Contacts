package me.safarov399.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "save_locations")
data class SaveLocationEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo var id: Long = 0,
    @ColumnInfo val numberOfContacts: Int = 0,
    @ColumnInfo val logo: String,
    @ColumnInfo val title: String
)