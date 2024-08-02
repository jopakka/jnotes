package joonas.niemi.jnotes.core.designsystem.theme

import androidx.compose.material3.Typography

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = KanitFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = KanitFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = KanitFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = KanitFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = KanitFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = KanitFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = KanitFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = KanitFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = KanitFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = MontserratFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = MontserratFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = MontserratFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = MontserratFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = MontserratFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = MontserratFamily),
)

