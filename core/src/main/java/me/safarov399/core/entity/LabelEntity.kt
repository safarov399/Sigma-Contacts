package me.safarov399.core.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import me.safarov399.core.base.BaseEntity

@Entity(tableName = "labels")
data class LabelEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo override var id: Long = 0,
    @ColumnInfo var numberOfContacts: Int = 0,
    @ColumnInfo val name: String
): BaseEntity