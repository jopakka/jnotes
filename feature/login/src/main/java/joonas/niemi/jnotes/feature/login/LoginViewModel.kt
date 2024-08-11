package joonas.niemi.jnotes.feature.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import joonas.niemi.jnotes.core.data.repository.UserRepository
import joonas.niemi.jnotes.core.data.util.UserAlreadyExists
import joonas.niemi.jnotes.core.data.util.UserNotFound
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalFoundationApi::class)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val mUiState = MutableStateFlow(LoginUiState())
    val uiState = mUiState.asStateFlow()

    val email: TextFieldState = TextFieldState()
    val password: TextFieldState = TextFieldState()
    val confirmPassword: TextFieldState = TextFieldState()

    fun onLogin() {
        val email = email.text.trim().toString()
        val password = password.text.toString()
        if (!isEmailValid(email) || !isPasswordValid(password)) {
            return
        }

        updateSigningState(SigningState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.login(email, password)
                updateSigningState(null)
            } catch (e: UserNotFound) {
                updateSigningState(SigningState.Error(R.string.feature_login_login_error_invalid_credentials))
            } catch (e: Exception) {
                Timber.e("Login failed: ${e.message}")
                updateSigningState(SigningState.Error(R.string.feature_login_login_error_unknown))
            }
        }
    }

    fun onSignup() {
        val email = email.text.trim().toString()
        val password = password.text.toString()
        val confirmPassword = confirmPassword.text.toString()
        if (!isEmailValid(email) || !isPasswordValid(password) || confirmPassword != password) {
            return
        }

        updateSigningState(SigningState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.register(email, password)
                updateSigningState(null)
            } catch (e: UserAlreadyExists) {
                updateSigningState(SigningState.Error(R.string.feature_login_signup_error_user_already_exists))
            } catch (e: Exception) {
                Timber.e("Register failed: ${e.message}")
                updateSigningState(SigningState.Error(R.string.feature_login_login_error_unknown))
            }
        }
    }

    fun isEmailValid(email: String): Boolean {
        return "^\\S+@\\S+\\.\\S+\$".toRegex().matches(email)
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    fun clearSignupFields() {
        email.clearText()
        password.clearText()
        confirmPassword.clearText()
        updateSigningState(null)
    }

    private fun updateSigningState(state: SigningState?) {
        mUiState.update { it.copy(signingState = state) }
    }
}