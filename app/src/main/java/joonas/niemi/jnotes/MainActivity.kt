package joonas.niemi.jnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import joonas.niemi.jnotes.core.data.util.TimeZoneMonitor
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme
import joonas.niemi.jnotes.core.ui.LocalTimeZone
import joonas.niemi.jnotes.ui.JNotesApp
import joonas.niemi.jnotes.ui.rememberJNotesAppState
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var timeZoneMonitor: TimeZoneMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberJNotesAppState(
                timeZoneMonitor = timeZoneMonitor,
            )

            val currentTimeZone by appState.currentTimeZone.collectAsStateWithLifecycle()

            CompositionLocalProvider(
                LocalTimeZone provides currentTimeZone,
            ) {
                JNotesTheme {
                    Box(
                        modifier = Modifier
                            .systemBarsPadding()
                            .fillMaxSize(),
                    ) {
                        JNotesApp()
                    }
                }
            }
        }
    }
}
