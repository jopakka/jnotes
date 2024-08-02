plugins {
    alias(libs.plugins.jnotes.android.library)
    alias(libs.plugins.jnotes.android.hilt)
    alias(libs.plugins.jnotes.android.library.firebase)
}

android {
    namespace = "joonas.niemi.jnotes.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}