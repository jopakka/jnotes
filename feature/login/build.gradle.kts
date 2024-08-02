plugins {
    alias(libs.plugins.jnotes.android.feature)
    alias(libs.plugins.jnotes.android.library.compose)
}

android {
    namespace = "joonas.niemi.jnotes.feature.login"
}

dependencies {
    implementation(projects.core.data)

    implementation(libs.androidx.compose.navigation)
    implementation(libs.timber)
}