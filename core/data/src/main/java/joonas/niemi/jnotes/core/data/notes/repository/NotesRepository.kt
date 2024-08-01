package joonas.niemi.jnotes.core.data.notes.repository

import joonas.niemi.jnotes.core.model.Note
import kotlinx.coroutines.flow.StateFlow

interface NotesRepository {
    val notes: StateFlow<Map<String, Note>>
}