plugins {
    alias(libs.plugins.jnotes.android.library)
    alias(libs.plugins.jnotes.android.hilt)
    alias(libs.plugins.jnotes.android.library.firebase)
}

android {
    namespace = "joonas.niemi.jnotes.core.domain"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(libs.timber)
}