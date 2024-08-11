package joonas.niemi.jnotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import joonas.niemi.jnotes.core.data.repository.NotesRepository
import joonas.niemi.jnotes.core.data.repository.UserRepository
import joonas.niemi.jnotes.core.model.NoteType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class JNotesAppViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val notesRepository: NotesRepository,
) : ViewModel() {
    private val mUiState = MutableStateFlow(JNotesAppUiState())
    val uiState = mUiState.asStateFlow()

    fun logout() {
        userRepository.logout()
    }

    fun addNote(type: NoteType, title: String, onCreated: (id: String) -> Unit) {
        updateIsCreatingNote(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val createdNoteId = notesRepository.addNote(
                    title,
                    "",
                    type
                )
                if (createdNoteId != null) {
                    withContext(Dispatchers.Main) {
                        onCreated(createdNoteId)
                    }
                }
            } catch (e: Exception) {
                Timber.e("Error adding note: ${e.message}")
            } finally {
                updateIsCreatingNote(false)
            }
        }
    }

    private fun updateIsCreatingNote(isLoading: Boolean) {
        mUiState.update { it.copy(isCreatingNote = isLoading) }
    }
}