package joonas.niemi.jnotes.feature.notelist.ui

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme
import joonas.niemi.jnotes.core.model.Note
import java.util.Locale

@Composable
internal fun NoteItem(note: Note) {
    val createdAtText = remember(note.createdAt) {
        try {
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.ROOT)
            sdf.format(note.createdAt)
        } catch (e: Exception) {
            ""
        }
    }

    Card(
        modifier = Modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Text(text = createdAtText, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview
@Composable
private fun PreviewNoteItem() {
    JNotesTheme {
        NoteItem(
            note = Note(
                id = "1",
                title = "Title",
                content = "Content",
                userId = "",
                createdAt = 1722407777000,
            )
        )
    }
}