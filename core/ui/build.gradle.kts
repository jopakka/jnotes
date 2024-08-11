plugins {
    alias(libs.plugins.jnotes.android.library)
    alias(libs.plugins.jnotes.android.library.compose)
}

android {
    namespace = "joonas.niemi.jnotes.core.ui"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)

    implementation(libs.kotlinx.datetime)
}