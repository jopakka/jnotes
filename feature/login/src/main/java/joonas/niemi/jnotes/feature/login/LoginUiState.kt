package joonas.niemi.jnotes.feature.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val signingState: SigningState? = null,
)
