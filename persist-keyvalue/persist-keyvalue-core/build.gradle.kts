plugins {
    kotlin("multiplatform")
    id("tz.co.asoft.library")
    id("io.codearte.nexus-staging")
    signing
}

kotlin {
    multiplatformLib()
}

aSoftOSSLibrary(
    version = vers.asoft.persist,
    description = "Platform agnostic contracts for persisting key-value pairs"
)