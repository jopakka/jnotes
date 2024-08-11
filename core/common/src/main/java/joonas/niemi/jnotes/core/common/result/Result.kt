package joonas.niemi.jnotes.core.common.result

sealed interface Result<out T> {
    data object Loading : Result<Nothing>
    data class Error(val exception: Throwable) : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
}