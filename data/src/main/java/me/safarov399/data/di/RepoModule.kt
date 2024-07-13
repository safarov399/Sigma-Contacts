package me.safarov399.data.di

import me.safarov399.data.dao.ContactDao
import me.safarov399.data.repo.ContactRepository
import me.safarov399.domain.repo.AbstractContactRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.safarov399.data.dao.LabelDao
import me.safarov399.data.dao.SaveLocationDao
import me.safarov399.data.repo.LabelRepository
import me.safarov399.data.repo.SaveLocationRepository
import me.safarov399.domain.repo.AbstractLabelRepository
import me.safarov399.domain.repo.AbstractSaveLocationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    @Singleton
    fun provideContactRepository(contactDao: ContactDao): AbstractContactRepository {
        return ContactRepository(contactDao)
    }

    @Provides
    @Singleton
    fun provideSaveLocationRepository(saveLocationDao: SaveLocationDao): AbstractSaveLocationRepository {
        return SaveLocationRepository(saveLocationDao)
    }

    @Provides
    @Singleton
    fun provideLabelRepository(labelDao: LabelDao): AbstractLabelRepository {
        return LabelRepository(labelDao)
    }
}