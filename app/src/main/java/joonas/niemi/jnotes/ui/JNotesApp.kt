package joonas.niemi.jnotes.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import joonas.niemi.jnotes.R
import joonas.niemi.jnotes.feature.notelist.NotesView
import joonas.niemi.jnotes.feature.notelist.ui.AddFab
import joonas.niemi.jnotes.ui.dialog.LogoutDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JNotesApp(
    viewModel: JNotesAppViewModel = hiltViewModel(),
) {
    var logoutConfirmVisible by remember { mutableStateOf(false) }
    if (logoutConfirmVisible) {
        LogoutDialog(
            onDismissRequest = { logoutConfirmVisible = false },
            onConfirm = {
                logoutConfirmVisible = false
                viewModel.logout()
            },
        )
    }

    Scaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        floatingActionButton = {
            AddFab(onAdd = {})
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { logoutConfirmVisible = true }) {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    }
                },
            )
        }
    ) { innerPadding ->
        NotesView(modifier = Modifier.padding(innerPadding))
    }
}