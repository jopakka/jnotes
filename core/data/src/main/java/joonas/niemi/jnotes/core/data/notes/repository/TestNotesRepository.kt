package joonas.niemi.jnotes.core.data.notes.repository

import joonas.niemi.jnotes.core.model.Note
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TestNotesRepository @Inject constructor() : NotesRepository {
    private val mNotes = MutableStateFlow(
        buildMap {
            val temp = mutableMapOf<String, Note>()
            repeat(100) {
                val id = it.toString()
                temp[id] = Note(
                    id = id,
                    title = "Title $id",
                    content = "Content $id",
                    createdAt = 1000000,
                    userId = "userId",
                )
            }
            putAll(temp)
        }
    )

    override val notes: StateFlow<Map<String, Note>> = mNotes.asStateFlow()
}