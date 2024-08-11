package joonas.niemi.jnotes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import joonas.niemi.jnotes.core.database.dao.TextNotesDao
import joonas.niemi.jnotes.core.database.model.TextNoteEntity
import joonas.niemi.jnotes.core.database.util.InstantConverter
import joonas.niemi.jnotes.core.database.util.NoteTypeConverter

@Database(
    entities = [TextNoteEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    NoteTypeConverter::class,
)
abstract class JDatabase : RoomDatabase() {
    abstract fun textNotesDao(): TextNotesDao
}