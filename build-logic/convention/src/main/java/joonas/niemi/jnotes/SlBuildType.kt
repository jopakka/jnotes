package joonas.niemi.jnotes

/**
 * This is shared between :app and :benchmarks module to provide configurations type safety.
 */
enum class SlBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}
