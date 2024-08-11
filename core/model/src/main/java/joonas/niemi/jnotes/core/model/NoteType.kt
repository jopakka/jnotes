package joonas.niemi.jnotes.core.model

enum class NoteType {
    TEXT,
    CHECKLIST;

    companion object {
        fun fromString(value: String): NoteType? {
            return when (value.lowercase()) {
                "text" -> TEXT
                "checklist" -> CHECKLIST
                else -> null
            }
        }
    }
}