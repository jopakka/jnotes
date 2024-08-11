package joonas.niemi.jnotes.feature.notelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import joonas.niemi.jnotes.core.common.result.Result
import joonas.niemi.jnotes.core.data.repository.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    notesRepository: NotesRepository,
) : ViewModel() {
    val notes = notesRepository.notes.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = Result.Loading,
    )
}