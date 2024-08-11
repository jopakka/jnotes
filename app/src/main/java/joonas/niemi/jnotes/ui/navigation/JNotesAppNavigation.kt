package joonas.niemi.jnotes.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import joonas.niemi.jnotes.R
import joonas.niemi.jnotes.core.model.NoteType
import joonas.niemi.jnotes.feature.notelist.NotesView
import joonas.niemi.jnotes.features.note.NoteEditScreen

@Composable
fun JNotesAppNavigation(
    onAddNoteType: (NoteType) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = JNotesScreen.Notes.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(JNotesScreen.Notes.route) {
            NotesView(
                modifier = Modifier.fillMaxSize(),
                onNoteOnClick = { noteId ->
                    navController.navigateToEditNote(noteId)
                },
                title = stringResource(R.string.app_name),
                onAddNoteType = onAddNoteType,
            )
        }

        composable(
            "${JNotesScreen.EditNote.route}/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType }),
        ) {
            NoteEditScreen(
                modifier = Modifier.fillMaxSize(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

internal fun NavHostController.navigateToEditNote(noteId: String) {
    navigate("${JNotesScreen.EditNote.route}/$noteId")
}
