package joonas.niemi.jnotes.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import joonas.niemi.jnotes.feature.notelist.NotesView
import joonas.niemi.jnotes.feature.notelist.ui.AddFab

@Composable
fun JNotesApp() {
    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        floatingActionButton = {
            AddFab(onAdd = {})
        },
    ) { innerPadding ->
        NotesView(modifier = Modifier.padding(innerPadding))
    }
}