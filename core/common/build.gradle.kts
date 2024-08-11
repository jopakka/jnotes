plugins {
    alias(libs.plugins.jnotes.android.library)
    alias(libs.plugins.jnotes.android.hilt)
}

android {
    namespace = "joonas.niemi.jnotes.core.common"
}

dependencies {
    implementation(libs.androidx.lifecycle.runtime.ktx)
}