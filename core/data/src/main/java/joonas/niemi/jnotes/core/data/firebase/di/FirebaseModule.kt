package joonas.niemi.jnotes.core.data.firebase.di

import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.app
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    fun provideFirebaseApp(): FirebaseApp = Firebase.app

    @Provides
    fun provideFirebaseFirestore(app: FirebaseApp): FirebaseFirestore =
        FirebaseFirestore.getInstance(app)

    @Provides
    fun provideFirebaseAuth(app: FirebaseApp): FirebaseAuth =
        FirebaseAuth.getInstance(app)
}