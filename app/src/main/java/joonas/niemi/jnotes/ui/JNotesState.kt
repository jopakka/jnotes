package joonas.niemi.jnotes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import joonas.niemi.jnotes.ui.navigation.JNotesScreen

@Stable
class JNotesState(
    val navController: NavHostController,
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: JNotesScreen?
        @Composable get() = when (currentDestination?.route) {
            JNotesScreen.Notes.route -> JNotesScreen.Notes
            JNotesScreen.EditNote.route -> JNotesScreen.EditNote
            else -> null
        }

    val showAddFab: Boolean
        @Composable get() = currentTopLevelDestination == JNotesScreen.Notes
}

@Composable
fun rememberJNotesState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    JNotesState(navController)
}
