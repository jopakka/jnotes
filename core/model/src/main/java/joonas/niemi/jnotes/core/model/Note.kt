package joonas.niemi.jnotes.core.model

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: Long,
    val userId: String,
)
