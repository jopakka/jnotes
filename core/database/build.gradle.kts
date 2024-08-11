plugins {
    alias(libs.plugins.jnotes.android.library)
    alias(libs.plugins.jnotes.android.room)
    alias(libs.plugins.jnotes.android.hilt)
}

android {
    namespace = "joonas.niemi.jnotes.core.database"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.kotlinx.datetime)
}