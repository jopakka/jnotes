package joonas.niemi.jnotes.core.data.notes.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import joonas.niemi.jnotes.core.data.notes.repository.NotesRepository
import joonas.niemi.jnotes.core.data.notes.repository.TestNotesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class NotesModule {
    @Binds
    abstract fun bindNotesRepository(impl: TestNotesRepository): NotesRepository
}