package joonas.niemi.jnotes.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme

@Composable
fun JButton(
    onClick: () -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Button(onClick = onClick, enabled = enabled, modifier = modifier) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(JButtonDefaults.objectSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when {
                loading -> CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = LocalContentColor.current,
                    strokeWidth = 2.dp,
                )

                leadingIcon != null -> leadingIcon()
            }

            Text(text = label)

            if (trailingIcon != null) {
                trailingIcon()
            }
        }
    }
}

private object JButtonDefaults {
    val objectSpacing: Dp = 8.dp
}

@Preview
@Composable
private fun PreviewJButton() {
    JNotesTheme {
        JButton(onClick = { /*TODO*/ }, label = "Button")
    }
}

@Preview
@Composable
private fun PreviewJButtonLeadingIcon() {
    JNotesTheme {
        JButton(
            onClick = { /*TODO*/ },
            label = "Button",
            leadingIcon = {
                Icon(Icons.Default.Star, contentDescription = null)
            },
        )
    }
}

@Preview
@Composable
private fun PreviewJButtonTrailingIcon() {
    JNotesTheme {
        JButton(
            onClick = { /*TODO*/ },
            label = "Button",
            trailingIcon = {
                Icon(Icons.Default.Star, contentDescription = null)
            },
        )
    }
}

@Preview
@Composable
private fun PreviewJButtonLoading() {
    JNotesTheme {
        JButton(
            onClick = { /*TODO*/ },
            label = "Button",
            leadingIcon = {
                Icon(Icons.Default.Star, contentDescription = null)
            },
            loading = true,
        )
    }
}
