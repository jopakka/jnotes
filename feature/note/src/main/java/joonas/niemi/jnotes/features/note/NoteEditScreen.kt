package joonas.niemi.jnotes.features.note

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import joonas.niemi.jnotes.core.common.result.Result
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme
import joonas.niemi.jnotes.core.model.TextNote
import joonas.niemi.jnotes.features.note.ui.TextNoteEdit
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteEditScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NoteEditViewModel = hiltViewModel(),
) {
    val note by viewModel.note.collectAsStateWithLifecycle()

    val noteData = remember(note) {
        when (val safeNote = note) {
            is Result.Success -> safeNote.data
            else -> null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = noteData?.title ?: "") },
                actions = {
                    var expanded by remember { mutableStateOf(false) }

                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = null)
                    }

                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        if (noteData != null) {
                            DropdownMenuItem(
                                text = { Text(text = "DELETE") },
                                onClick = {
                                    noteData.let(viewModel::deleteNote)
                                    expanded = false
                                    onNavigateBack()
                                },
                            )
                        }
                    }
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        val innerModifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = JNotesTheme.spacing.screenPadding)
            .fillMaxSize()

        when (val safeNote = note) {
            is Result.Success -> {
                when (noteData) {
                    is TextNote -> TextNoteEdit(
                        note = noteData,
                        modifier = innerModifier,
                        onSaveNote = viewModel::editNote,
                    )

                    null -> Text(text = "Note not found")
                }
            }

            is Result.Loading -> {
                Column(
                    modifier = innerModifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            is Result.Error -> {
                LaunchedEffect(safeNote.exception) {
                    Timber.e("Error getting note: ${safeNote.exception.message}")
                }
                Text(text = "Error getting note")
            }
        }
    }
}