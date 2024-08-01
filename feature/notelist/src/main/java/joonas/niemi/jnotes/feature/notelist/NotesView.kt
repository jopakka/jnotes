package joonas.niemi.jnotes.feature.notelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme
import joonas.niemi.jnotes.core.model.Note
import joonas.niemi.jnotes.feature.notelist.ui.NoteItem

@Composable
fun NotesView(
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsStateWithLifecycle()

    NotesViewContent(modifier = modifier, notes = notes)
}

@Composable
private fun NotesViewContent(
    notes: Map<String, Note>,
    modifier: Modifier = Modifier,
) {
    val noteValues = remember(notes.values) { notes.values.toList() }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = noteValues,
            key = { it.id },
        ) {
            NoteItem(note = it)
        }
    }
}

@Preview
@Composable
private fun NotesViewPreview() {
    JNotesTheme {
        NotesViewContent(
            notes = mapOf(
                "1" to Note(
                    id = "1",
                    title = "Title 1",
                    content = "Content 1",
                    userId = "",
                    createdAt = 1722407777000
                ),
                "2" to Note(
                    id = "2",
                    title = "Title 2",
                    content = "Content 2",
                    userId = "",
                    createdAt = 1712407777000
                ),
                "3" to Note(
                    id = "3",
                    title = "Title 3",
                    content = "Content 3",
                    userId = "",
                    createdAt = 1702407777000
                ),
                "4" to Note(
                    id = "4",
                    title = "Title 4",
                    content = "Content 4",
                    userId = "",
                    createdAt = 1682407777000
                ),
            )
        )
    }
}