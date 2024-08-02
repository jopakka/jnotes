package joonas.niemi.jnotes.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun InputLabel(text: String) {
    Text(text = text, style = MaterialTheme.typography.labelLarge)
}