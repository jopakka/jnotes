package joonas.niemi.jnotes

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configure Firebase
 */
internal fun Project.configureFirebase(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        dependencies {
            val bom = libs.findLibrary("firebase-bom").get()
            add("implementation", platform(bom))
            add("implementation", libs.findLibrary("firebase.auth").get())
            add("implementation", libs.findLibrary("firebase.analytics").get())
            add("implementation", libs.findLibrary("firebase.crashlytics").get())
            add("implementation", libs.findLibrary("firebase.firestore").get())
        }
    }
}