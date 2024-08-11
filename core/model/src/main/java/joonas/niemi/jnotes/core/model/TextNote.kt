package joonas.niemi.jnotes.core.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class TextNote(
    override val id: String,
    override val title: String,
    val content: String,
    override val userId: String,
    override val createdAt: Instant,
) : Note {
    override val type: NoteType = NoteType.TEXT

    companion object {
        val preview = TextNote(
            id = "1",
            title = "Title",
            content = "Content",
            userId = "1",
            createdAt = Clock.System.now(),
        )
    }
}
