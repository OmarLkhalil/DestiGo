pluginManagement {
    repositories {
        google()
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

rootProject.name = "DestiGo"
include(":app")
include(":ui:auth")
include(":core:domain")
include(":core:data")
include(":ui:home")
include(":common-ui")
