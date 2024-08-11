package joonas.niemi.jnotes.core.data.util

class UserAlreadyExists(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception()

class UserNotFound(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception()
