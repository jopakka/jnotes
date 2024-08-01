plugins {
    alias(libs.plugins.jnotes.android.library)
    alias(libs.plugins.jnotes.android.hilt)
}

android {
    namespace = "joonas.niemi.jnotes.core.data"
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}