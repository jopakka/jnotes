package joonas.niemi.jnotes.core.data.repository

import joonas.niemi.jnotes.core.common.result.Result
import joonas.niemi.jnotes.core.model.Note
import joonas.niemi.jnotes.core.model.NoteType
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNoteById(id: String): Flow<Result<Note?>>
    val notes: Flow<Result<Map<String, Note>>>
    suspend fun addNote(title: String, content: String, type: NoteType): String
    suspend fun editNote(note: Note)
    suspend fun deleteNote(note: Note)
}