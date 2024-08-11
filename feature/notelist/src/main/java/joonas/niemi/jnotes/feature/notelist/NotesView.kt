package joonas.niemi.jnotes.feature.notelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import joonas.niemi.jnotes.core.common.result.Result
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme
import joonas.niemi.jnotes.core.model.Note
import joonas.niemi.jnotes.core.model.NoteType
import joonas.niemi.jnotes.core.model.TextNote
import joonas.niemi.jnotes.core.ui.NoteItem
import joonas.niemi.jnotes.feature.notelist.ui.AddFab
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesView(
    title: String,
    onNoteOnClick: (String) -> Unit,
    onAddNoteType: (NoteType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            AddFab(
                onAdd = onAddNoteType,
            )
        },
        topBar = {
            TopAppBar(title = { Text(text = title) })
        }
    ) { innerPadding ->
        val innerModifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = JNotesTheme.spacing.screenPadding)
            .fillMaxWidth()
        when (val safeNotes = notes) {
            is Result.Success -> {
                NotesViewContent(
                    modifier = innerModifier,
                    notes = safeNotes.data,
                    onNoteOnClick = onNoteOnClick,
                )
            }

            is Result.Loading -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = innerModifier,
                ) {
                    CircularProgressIndicator()
                }
            }

            is Result.Error -> {
                LaunchedEffect(safeNotes.exception) {
                    Timber.e("Error getting notes: ${safeNotes.exception.message}")
                }
                Column(
                    modifier = innerModifier,
                ) {
                    Text(text = "Error getting notes")
                }
            }
        }
    }
}

@Composable
private fun NotesViewContent(
    notes: Map<String, Note>,
    onNoteOnClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val noteValues = remember(notes.values) { notes.values.toList() }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (noteValues.isEmpty()) {
            item {
                Text(text = "No notes")
            }
        }

        items(
            items = noteValues,
            key = { it.id },
        ) {
            NoteItem(note = it, onNoteOnClick = onNoteOnClick)
        }
    }
}

@Preview
@Composable
private fun NotesViewPreview() {
    JNotesTheme {
        NotesViewContent(
            notes = buildMap {
                repeat(20) {
                    put(it.toString(), TextNote.preview)
                }
            },
            onNoteOnClick = {},
        )
    }
}