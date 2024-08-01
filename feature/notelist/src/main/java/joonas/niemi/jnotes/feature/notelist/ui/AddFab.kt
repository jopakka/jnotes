package joonas.niemi.jnotes.feature.notelist.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import joonas.niemi.jnotes.core.model.NoteType
import joonas.niemi.jnotes.feature.notelist.R

@Composable
fun AddFab(onAdd: (NoteType) -> Unit) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.End) {
        if (isExpanded) {
            FabList(onAdd = onAdd)
            Box(modifier = Modifier.height(8.dp))
        }
        FloatingActionButton(
            onClick = { isExpanded = !isExpanded },
        ) {
            Crossfade(targetState = isExpanded, label = "Add icon swap") {
                if (it) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                } else {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
    }
}

@Composable
private fun FabList(
    onAdd: (NoteType) -> Unit,
) {
    FabMenuItem(
        onClick = { onAdd(NoteType.TEXT) },
        painter = painterResource(R.drawable.baseline_short_text_24),
        label = "Text",
    )
    FabMenuItem(
        onClick = { onAdd(NoteType.CHECKLIST) },
        painter = painterResource(R.drawable.baseline_checklist_24),
        label = "List",
    )
}

@Composable
private fun FabMenuItem(
    painter: Painter,
    label: String,
    onClick: () -> Unit,
    contentDescription: String? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FabMenuLabel(label = label)
        SmallFloatingActionButton(
            onClick = onClick,
        ) {
            Icon(painter = painter, contentDescription = contentDescription)
        }
    }
}

@Composable
private fun FabMenuLabel(
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = Color.Black.copy(alpha = 0.8f)
    ) {
        Text(
            text = label,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelLarge,
            maxLines = 1
        )
    }
}