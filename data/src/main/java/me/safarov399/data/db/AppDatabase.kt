package me.safarov399.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.safarov399.core.converter.LocalDatePairConverter
import me.safarov399.core.converter.PhoneNumbersConverter
import me.safarov399.core.entity.ContactEntity
import me.safarov399.core.entity.LabelEntity
import me.safarov399.core.entity.SaveLocationEntity
import me.safarov399.data.dao.ContactDao
import me.safarov399.data.dao.LabelDao
import me.safarov399.data.dao.SaveLocationDao

@Database(
    entities = [ContactEntity::class, SaveLocationEntity::class, LabelEntity::class], version = 1, exportSchema = false
)
@TypeConverters(LocalDatePairConverter::class, PhoneNumbersConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactDao
    abstract fun saveLocationDao(): SaveLocationDao
    abstract fun labelDao(): LabelDao
}