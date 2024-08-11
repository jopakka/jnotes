package joonas.niemi.jnotes.features.note.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import joonas.niemi.jnotes.core.designsystem.component.JTextFieldDefaults
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme
import joonas.niemi.jnotes.core.model.Note
import joonas.niemi.jnotes.core.model.TextNote
import joonas.niemi.jnotes.features.note.R
import kotlinx.datetime.Clock

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TextNoteEdit(
    note: TextNote,
    onSaveNote: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textStyle = LocalTextStyle.current
    var noteValue by remember(note.content) {
        mutableStateOf(
            TextFieldValue(
                note.content,
                TextRange(note.content.length)
            )
        )
    }
    val placeholderTextStyle = textStyle.copy(color = textStyle.color.copy(alpha = 0.5f))
    val (focusRequester) = FocusRequester.createRefs()

    LaunchedEffect(note.content) {
        noteValue = noteValue.copy(text = note.content)
    }

    DisposableEffect(Unit) {
        onDispose {
            onSaveNote(note.copy(content = noteValue.text.trim()))
        }
    }

    val showPlaceholder by remember { derivedStateOf { noteValue.text.isEmpty() } }

    val decorator: @Composable (@Composable () -> Unit) -> Unit = {
        JTextFieldDefaults.PlaceholderDecorator(
            showPlaceholder = showPlaceholder,
            placeholder = stringResource(R.string.feature_note_start_writing_note),
            maxLines = Int.MAX_VALUE,
            placeholderTextStyle = placeholderTextStyle,
            content = it,
        )
    }

    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
            ) {
                focusRequester.requestFocus()
            }
            .then(modifier),
    ) {
        BasicTextField(
            value = noteValue,
            onValueChange = {
                noteValue = it
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            textStyle = textStyle,
            decorationBox = {
                Box(modifier = Modifier.drawBehind {
                    val lineWidth = 1.dp.toPx()
                    val lineHeight = textStyle.lineHeight.toPx()

                    val lineCount = (size.height / lineHeight).toInt()
                    for (i in 1..lineCount) {
                        val lineOffset = i * lineHeight
                        drawLine(
                            color = Color(0x54808080),
                            start = Offset(0f, lineOffset),
                            end = Offset(size.width, lineOffset),
                            strokeWidth = lineWidth,
                        )
                    }
                }) {
                    decorator(it)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTextNoteEdit() {
    JNotesTheme {
        TextNoteEdit(
            note = TextNote(
                "id",
                "Title",
                "Content\nContent\nContent\nContent\nContent\nContent\n",
                "userId",
                Clock.System.now(),
            ),
            onSaveNote = {},
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
        )
    }
}