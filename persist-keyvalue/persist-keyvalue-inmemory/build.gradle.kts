plugins {
    kotlin("multiplatform")
    id("tz.co.asoft.library")
    id("io.codearte.nexus-staging")
    signing
}

kotlin {
    multiplatformLib()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":persist-keyvalue-core"))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.persist,
    description = "Platform agnostic contracts for persisting key-value pairs in memory"
)