package joonas.niemi.jnotes.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import joonas.niemi.jnotes.core.model.NoteType
import joonas.niemi.jnotes.ui.dialog.NoteTitleDialog
import joonas.niemi.jnotes.ui.navigation.JNotesAppNavigation
import joonas.niemi.jnotes.ui.navigation.navigateToEditNote

@Composable
fun JNotesApp(
    jNotesState: JNotesState = rememberJNotesState(),
    viewModel: JNotesAppViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedNoteType by remember { mutableStateOf<NoteType?>(null) }
    var noteTitleDialogVisible by remember { mutableStateOf(false) }
    if (noteTitleDialogVisible) {
        NoteTitleDialog(
            onDismissRequest = {
                noteTitleDialogVisible = false
                selectedNoteType = null
            },
            note = null,
            onNoteTitle = {
                val noteType = selectedNoteType ?: return@NoteTitleDialog
                viewModel.addNote(noteType, it) { noteId ->
                    noteTitleDialogVisible = false
                    selectedNoteType = null
                    jNotesState.navController.navigateToEditNote(noteId)
                }
            },
            isLoading = uiState.isCreatingNote,
        )
    }

    JNotesAppNavigation(
        modifier = Modifier.fillMaxSize(),
        navController = jNotesState.navController,
        onAddNoteType = {
            selectedNoteType = it
            noteTitleDialogVisible = true
        },
    )
}