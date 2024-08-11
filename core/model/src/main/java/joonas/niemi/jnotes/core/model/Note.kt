package joonas.niemi.jnotes.core.model

import kotlinx.datetime.Instant

interface Note {
    val id: String
    val title: String
    val userId: String
    val type: NoteType
    val createdAt: Instant
}
