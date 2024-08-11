package joonas.niemi.jnotes.core.data.model

import joonas.niemi.jnotes.core.database.model.TextNoteEntity
import joonas.niemi.jnotes.core.model.TextNote

fun TextNote.asEntity(): TextNoteEntity = TextNoteEntity(
    id = id,
    title = title,
    content = content,
    userId = userId,
    createdAt = createdAt,
)