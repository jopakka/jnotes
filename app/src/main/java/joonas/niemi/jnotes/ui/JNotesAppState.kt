package joonas.niemi.jnotes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import joonas.niemi.jnotes.core.data.util.TimeZoneMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone

@Composable
fun rememberJNotesAppState(
    timeZoneMonitor: TimeZoneMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): JNotesAppState {
    return remember(timeZoneMonitor, coroutineScope) {
        JNotesAppState(coroutineScope, timeZoneMonitor)
    }
}

@Stable
class JNotesAppState(
    coroutineScope: CoroutineScope,
    timeZoneMonitor: TimeZoneMonitor,
) {
    val currentTimeZone = timeZoneMonitor.currentTimeZone
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(5_000),
            TimeZone.currentSystemDefault(),
        )
}