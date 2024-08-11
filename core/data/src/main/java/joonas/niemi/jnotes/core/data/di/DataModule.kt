package joonas.niemi.jnotes.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import joonas.niemi.jnotes.core.data.repository.FirebaseUserRepository
import joonas.niemi.jnotes.core.data.repository.NotesRepository
import joonas.niemi.jnotes.core.data.repository.OfflineFirstNotesRepository
import joonas.niemi.jnotes.core.data.repository.UserRepository
import joonas.niemi.jnotes.core.data.util.TimeZoneBroadcastMonitor
import joonas.niemi.jnotes.core.data.util.TimeZoneMonitor

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindNotesRepository(impl: OfflineFirstNotesRepository): NotesRepository

    @Binds
    internal abstract fun bindUserRepository(impl: FirebaseUserRepository): UserRepository

    @Binds
    internal abstract fun bindsTimeZoneMonitor(impl: TimeZoneBroadcastMonitor): TimeZoneMonitor
}