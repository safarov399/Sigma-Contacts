package me.safarov399.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import me.safarov399.data.dao.ContactDao
import me.safarov399.core.entity.ContactEntity

@Database(
    entities = [ContactEntity::class], version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun contactsDao(): ContactDao
}