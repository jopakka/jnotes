// TODO: Can be removed when https://issuetracker.google.com/issues/328871352 is fixed
gradle.startParameter.excludedTaskNames.addAll(
    listOf(
        ":build-logic:convention:testClasses",
    )
)

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "JNotes"
include(":app")

include(":core:assets")
include(":core:common")
include(":core:data")
include(":core:database")
include(":core:designsystem")
include(":core:domain")
include(":core:model")

include(":feature:login")
include(":feature:notelist")
include(":feature:note")
include(":core:ui")
