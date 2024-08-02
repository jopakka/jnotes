package joonas.niemi.jnotes.core.designsystem.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import joonas.niemi.jnotes.core.designsystem.R

internal val KanitFamily = FontFamily(
    Font(R.font.kanit_regular, FontWeight.Normal),
    Font(R.font.kanit_bold, FontWeight.Bold),
    Font(R.font.kanit_light, FontWeight.Light),
)

internal val MontserratFamily = FontFamily(
    Font(R.font.montserrat_regular, FontWeight.Normal),
    Font(R.font.montserrat_bold, FontWeight.Bold),
    Font(R.font.montserrat_light, FontWeight.Light),
    Font(R.font.montserrat_thin, FontWeight.Thin),
    Font(R.font.montserrat_medium, FontWeight.Medium),
    Font(R.font.montserrat_italic, FontWeight.Normal, FontStyle.Italic),
)
