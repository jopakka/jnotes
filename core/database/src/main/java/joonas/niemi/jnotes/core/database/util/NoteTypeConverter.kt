package joonas.niemi.jnotes.core.database.util

import androidx.room.TypeConverter
import joonas.niemi.jnotes.core.model.NoteType

internal class NoteTypeConverter {
    @TypeConverter
    fun stringToNoteType(value: String?): NoteType? =
        value?.let(NoteType::fromString)

    @TypeConverter
    fun noteTypeToString(type: NoteType?): String? = type?.name
}
