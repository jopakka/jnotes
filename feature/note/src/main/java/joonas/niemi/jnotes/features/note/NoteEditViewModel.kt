package joonas.niemi.jnotes.features.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import joonas.niemi.jnotes.core.common.result.Result
import joonas.niemi.jnotes.core.data.repository.NotesRepository
import joonas.niemi.jnotes.core.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val notesRepository: NotesRepository,
) : ViewModel() {
    private val noteId: String = checkNotNull(savedStateHandle["noteId"])

    val note = notesRepository.getNoteById(noteId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading,
    )

    fun editNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesRepository.editNote(note)
            } catch (e: Exception) {
                Timber.e("Error editing note: ${e.message}")
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                notesRepository.deleteNote(note)
            } catch (e: Exception) {
                Timber.e("Error deleting note: ${e.message}")
            }
        }
    }
}