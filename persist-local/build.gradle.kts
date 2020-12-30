plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("tz.co.asoft.library")
    id("io.codearte.nexus-staging")
    signing
}

android {
    configureAndroid("src/androidMain")
    defaultConfig {
        minSdk = 9
    }
    targetJava("1.8")
}

kotlin {
    universalLib()
}

aSoftOSSLibrary(
    version = vers.asoft.persist,
    description = "Platform agnostic contracts for persisting data in a local environment"
)