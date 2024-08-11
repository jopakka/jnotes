plugins {
    alias(libs.plugins.jnotes.android.library)
    alias(libs.plugins.jnotes.android.hilt)
    alias(libs.plugins.jnotes.android.library.firebase)
}

android {
    namespace = "joonas.niemi.jnotes.core.data"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.database)
    implementation(projects.core.model)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.datetime)
    implementation(libs.timber)
}