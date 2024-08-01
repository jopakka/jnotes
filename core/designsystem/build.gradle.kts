plugins {
    alias(libs.plugins.jnotes.android.library)
    alias(libs.plugins.jnotes.android.library.compose)
}

android {
    namespace = "joonas.niemi.jnotes.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.text.google.fonts)

    debugApi(libs.androidx.compose.ui.tooling)
}