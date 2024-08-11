package joonas.niemi.jnotes.core.data.repository

import com.google.firebase.auth.FirebaseUser
import joonas.niemi.jnotes.core.common.result.Result
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    val currentUser: FirebaseUser?
    fun user(): Flow<Result<FirebaseUser?>>

    suspend fun login(email: String, password: String)
    suspend fun register(email: String, password: String)
    fun logout()
}
