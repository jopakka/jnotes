package joonas.niemi.jnotes.core.data.repository

import joonas.niemi.jnotes.core.common.result.Result
import joonas.niemi.jnotes.core.data.model.asEntity
import joonas.niemi.jnotes.core.database.dao.TextNotesDao
import joonas.niemi.jnotes.core.database.model.asExternalModel
import joonas.niemi.jnotes.core.model.Note
import joonas.niemi.jnotes.core.model.NoteType
import joonas.niemi.jnotes.core.model.TextNote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import java.util.UUID
import javax.inject.Inject

class OfflineFirstNotesRepository @Inject constructor(
    private val textNotesDao: TextNotesDao,
) : NotesRepository {
    override val notes: Flow<Result<Map<String, Note>>> = textNotesDao.getAll()
        .distinctUntilChanged()
        .map { Result.Success(it.mapValues { n -> n.value.asExternalModel() }) }
        .catch { Result.Error(it) }

    override fun getNoteById(id: String): Flow<Result<Note?>> = textNotesDao.getById(id)
        .distinctUntilChanged()
        .map { Result.Success(it?.asExternalModel()) }
        .catch { Result.Error(it) }

    override suspend fun addNote(title: String, content: String, type: NoteType): String {
        val id = UUID.randomUUID().toString()
        when (type) {
            NoteType.TEXT -> {
                textNotesDao.insert(
                    TextNote(
                        id = id,
                        title = title,
                        content = content,
                        createdAt = Clock.System.now(),
                        userId = "local",
                    ).asEntity()
                )
            }

            NoteType.CHECKLIST -> {
                TODO("Not yet implemented")
            }
        }

        return id
    }

    override suspend fun editNote(note: Note) {
        when (note.type) {
            NoteType.TEXT -> textNotesDao.update((note as TextNote).asEntity())
            NoteType.CHECKLIST -> {
                TODO("Not yet implemented")
            }
        }
    }

    override suspend fun deleteNote(note: Note) {
        when (note.type) {
            NoteType.TEXT -> textNotesDao.delete((note as TextNote).asEntity())
            NoteType.CHECKLIST -> {
                TODO("Not yet implemented")
            }
        }
    }
}