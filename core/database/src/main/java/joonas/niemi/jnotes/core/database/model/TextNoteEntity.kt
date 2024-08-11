package joonas.niemi.jnotes.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import joonas.niemi.jnotes.core.model.TextNote
import kotlinx.datetime.Instant

@Entity(tableName = "text_notes")
data class TextNoteEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val createdAt: Instant,
    val userId: String,
)

fun TextNoteEntity.asExternalModel() = TextNote(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    userId = userId,
)
