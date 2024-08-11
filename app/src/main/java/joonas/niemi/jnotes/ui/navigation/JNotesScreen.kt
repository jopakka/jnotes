package joonas.niemi.jnotes.ui.navigation

sealed class JNotesScreen(val route: String) {
    data object Notes : JNotesScreen("notes")
    data object EditNote : JNotesScreen("editNote")
}