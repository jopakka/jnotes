package joonas.niemi.jnotes.core.data.user.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun user(): Flow<UserState>

    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    fun logout()
}

sealed class UserState {
    data object Loading : UserState()
    data class Success(val user: FirebaseUser?) : UserState()
}