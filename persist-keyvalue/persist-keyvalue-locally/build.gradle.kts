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
}

kotlin {
    multiplatformLib(forAndroid = true)
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":persist-keyvalue-core"))
            }
        }

        val jvmMain by getting {
            dependencies {
                api(asoft("kotlinx-serialization-mapper", vers.asoft.mapper))
            }
        }

        val jvmTest by getting {
            dependencies {
                api(asoft("test", vers.asoft.test))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.persist,
    description = "Platform agnostic contracts for persisting key-value pairs in a local environment"
)