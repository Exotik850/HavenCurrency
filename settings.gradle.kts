import dev.scaffoldit.hytale.wire.HytaleManifest

rootProject.name = "dev.byt3.haven.currency"

plugins {
    // See documentation on https://scaffoldit.dev
    id("dev.scaffoldit") version "0.2.+"
}

// Would you like to do a split project?
// Create a folder named "common", then configure details with `common { }`

hytale {
    usePatchline("release")
    useVersion("latest")

    repositories {
        // Any external repositories besides: MavenLocal, MavenCentral, HytaleMaven, and CurseMaven
    }

    dependencies {
        // Any external dependency you also want to include
    }

    manifest {
        Group = "Exotik850.Haven"
        Name = "Currency"
        Main = "dev.byt3.haven.currency.CurrencyPlugin"
        Description = "Adds a simple currency system to the game, with a user-friendly GUI."
        Version = "0.0.1"
        Dependencies = mapOf(
            "Hytale:EntityStatsModule" to "*"
        )
        Authors = listOf(HytaleManifest.Author("Exotik850"))
        ServerVersion = "2026.02.19-1a311a592"
    }
}