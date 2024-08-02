package joonas.niemi.jnotes.feature.login

import androidx.annotation.StringRes

sealed class SigningState {
    data object Loading : SigningState()
    data class Error(@StringRes val messageRes: Int) : SigningState()
}