package joonas.niemi.jnotes.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme
import joonas.niemi.jnotes.core.model.Note
import joonas.niemi.jnotes.core.model.TextNote
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@Composable
fun NoteItem(
    onNoteOnClick: (String) -> Unit,
    note: Note,
) {
    val density = LocalDensity.current

    val createdAtText = dateFormatted(note.createdAt)

    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onNoteOnClick(note.id) })
                .padding(8.dp)
        ) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val textStyle = MaterialTheme.typography.bodySmall
                val bodySmallLineHeight = with(density) {
                    textStyle.lineHeight.toDp()
                }
                Icon(
                    painter = painterResource(R.drawable.core_ui_outline_calendar_today_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(bodySmallLineHeight)
                        .padding(end = 4.dp)
                )
                Text(text = createdAtText, style = textStyle)
            }
        }
    }
}

@Composable
fun dateFormatted(publishDate: Instant): String = DateTimeFormatter
    .ofLocalizedDate(FormatStyle.SHORT)
    .withLocale(Locale.getDefault())
    .withZone(LocalTimeZone.current.toJavaZoneId())
    .format(publishDate.toJavaInstant())

@Preview
@Composable
private fun PreviewNoteItem() {
    JNotesTheme {
        NoteItem(
            note = TextNote.preview,
            onNoteOnClick = {},
        )
    }
}