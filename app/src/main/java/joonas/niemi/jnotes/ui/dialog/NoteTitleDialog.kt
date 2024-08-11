package joonas.niemi.jnotes.ui.dialog

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import joonas.niemi.jnotes.R
import joonas.niemi.jnotes.core.designsystem.component.JNotesAlertDialog
import joonas.niemi.jnotes.core.designsystem.component.JTextField
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme
import joonas.niemi.jnotes.core.model.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteTitleDialog(
    onNoteTitle: (String) -> Unit,
    onDismissRequest: () -> Unit,
    note: Note?,
    isLoading: Boolean,
) {
    val context = LocalContext.current
    val density = LocalDensity.current

    val title = remember(note) {
        if (note == null) {
            context.getString(R.string.add_title)
        } else {
            context.getString(R.string.edit_title)
        }
    }

    val state = rememberTextFieldState(note?.title ?: "")

    JNotesAlertDialog(
        title = {
            Row {
                Text(text = title)
                if (isLoading) {
                    val lineHeight = with(density) {
                        LocalTextStyle.current.lineHeight.toDp()
                    }
                    CircularProgressIndicator(
                        modifier = Modifier.size(lineHeight),
                        strokeWidth = 3.dp,
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onNoteTitle(state.text.toString()) },
                enabled = state.text.isNotEmpty(),
            ) {
                Text(text = stringResource(joonas.niemi.jnotes.core.assets.R.string.core_assets_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(joonas.niemi.jnotes.core.assets.R.string.core_assets_cancel))
            }
        },
        text = {
            JTextField(
                state = state,
                placeholder = stringResource(R.string.add_title),
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
            )
        },
        onDismissRequest = onDismissRequest,
    )
}

@Preview
@Composable
private fun PreviewNoteTitleDialog() {
    JNotesTheme {
        NoteTitleDialog(
            onNoteTitle = {},
            onDismissRequest = {},
            isLoading = true,
            note = null,
        )
    }
}