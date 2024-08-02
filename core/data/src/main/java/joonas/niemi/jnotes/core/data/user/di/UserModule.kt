package joonas.niemi.jnotes.core.data.user.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import joonas.niemi.jnotes.core.data.user.repository.FirebaseUserRepository
import joonas.niemi.jnotes.core.data.user.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindUserRepository(impl: FirebaseUserRepository): UserRepository
}