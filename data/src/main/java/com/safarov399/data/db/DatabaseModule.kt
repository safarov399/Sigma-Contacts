package com.safarov399.data.db

import android.content.Context
import androidx.room.Room
import com.safarov399.data.dao.ContactDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "notes-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideContactsDao(
        appDatabase: AppDatabase
    ): ContactDao {
        return appDatabase.contactsDao()
    }

}