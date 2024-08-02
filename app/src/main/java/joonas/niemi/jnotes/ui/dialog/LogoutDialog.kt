package joonas.niemi.jnotes.ui.dialog

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import joonas.niemi.jnotes.R
import joonas.niemi.jnotes.core.designsystem.component.JNotesAlertDialog

@Composable
fun LogoutDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    JNotesAlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        title = {
            Text(text = stringResource(R.string.logout_confirm_title))
        },
        text = {
            Text(text = stringResource(R.string.logout_confirm_text))
        },
        modifier = modifier,
    )
}