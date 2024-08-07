pluginManagement {
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

rootProject.name = "Sigma Contacts"
include(":app")
include(":feature")
include(":common")
include(":core")
include(":feature:home")
include(":feature:details")
include(":data")
include(":domain")
include(":feature:highlights")
include(":feature:organize")
include(":feature:add")
include(":feature:settings")
