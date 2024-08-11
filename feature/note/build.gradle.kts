plugins {
    alias(libs.plugins.jnotes.android.feature)
    alias(libs.plugins.jnotes.android.library.compose)
}

android {
    namespace = "joonas.niemi.jnotes.features.note"
}

dependencies {
    implementation(projects.core.assets)
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.model)

    implementation(libs.timber)
    implementation(libs.kotlinx.datetime)
}