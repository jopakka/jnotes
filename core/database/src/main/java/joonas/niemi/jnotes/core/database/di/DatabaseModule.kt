package joonas.niemi.jnotes.core.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import joonas.niemi.jnotes.core.database.JDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesJDatabase(
        @ApplicationContext context: Context,
    ): JDatabase = Room.databaseBuilder(
        context,
        JDatabase::class.java,
        "jnotes-database"
    ).build()
}