plugins {
    alias(libs.plugins.jnotes.android.feature)
    alias(libs.plugins.jnotes.android.library.compose)
}

android {
    namespace = "joonas.niemi.jnotes.feature.notelist"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(libs.timber)
}