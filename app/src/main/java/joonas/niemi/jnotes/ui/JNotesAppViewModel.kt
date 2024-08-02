package joonas.niemi.jnotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import joonas.niemi.jnotes.core.data.user.repository.UserRepository
import joonas.niemi.jnotes.core.data.user.repository.UserState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class JNotesAppViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    val user = userRepository.user().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UserState.Loading,
    )

    fun logout() {
        userRepository.logout()
    }
}