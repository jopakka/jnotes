package joonas.niemi.jnotes.core.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import joonas.niemi.jnotes.core.common.result.Result
import joonas.niemi.jnotes.core.data.util.UserAlreadyExists
import joonas.niemi.jnotes.core.data.util.UserNotFound
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseUserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : UserRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override fun user() = callbackFlow {
        val listener = AuthStateListener {
            trySend(Result.Success(it.currentUser))
        }
        firebaseAuth.addAuthStateListener(listener)

        awaitClose {
            firebaseAuth.removeAuthStateListener(listener)
        }
    }

    override suspend fun login(email: String, password: String) {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    if (it is FirebaseAuthInvalidCredentialsException) {
                        continuation.resumeWithException(UserNotFound(it.message, it))
                    } else {
                        continuation.resumeWithException(it)
                    }
                }
        }
    }

    override suspend fun register(email: String, password: String) {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    continuation.resume(Unit)
                }
                .addOnFailureListener {
                    if (it is FirebaseAuthUserCollisionException) {
                        continuation.resumeWithException(UserAlreadyExists(it.message, it))
                    } else {
                        continuation.resumeWithException(it)
                    }
                }
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}