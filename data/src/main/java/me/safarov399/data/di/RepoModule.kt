package me.safarov399.data.di

import me.safarov399.data.dao.ContactDao
import me.safarov399.data.repo.ContactRepository
import me.safarov399.domain.repo.AbstractContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.safarov399.data.dao.SaveLocationDao
import me.safarov399.data.repo.SaveLocationRepository
import me.safarov399.domain.repo.AbstractSaveLocationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Singleton
    @Provides
    fun provideContactRepository(contactDao: ContactDao): AbstractContactRepository {
        return ContactRepository(contactDao)
    }

    @Singleton
    @Provides
    fun provideSaveLocationRepository(saveLocationDao: SaveLocationDao): AbstractSaveLocationRepository {
        return SaveLocationRepository(saveLocationDao)
    }
}