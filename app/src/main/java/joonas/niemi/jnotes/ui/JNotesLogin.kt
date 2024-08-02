package joonas.niemi.jnotes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import joonas.niemi.jnotes.core.data.user.repository.UserState
import joonas.niemi.jnotes.feature.login.LoginScreen

@Composable
fun JNotesLogin(
    viewModel: JNotesLoginViewModel = hiltViewModel(),
) {
    val userState by viewModel.user.collectAsStateWithLifecycle()

    when (val state = userState) {
        is UserState.Loading -> {}
        is UserState.Success -> {
            if (state.user == null) {
                LoginScreen()
            } else {
                JNotesApp()
            }
        }
    }
}