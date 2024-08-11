package joonas.niemi.jnotes.core.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import joonas.niemi.jnotes.core.database.JDatabase
import joonas.niemi.jnotes.core.database.dao.TextNotesDao

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesTextNotesDao(
        database: JDatabase,
    ): TextNotesDao = database.textNotesDao()
}