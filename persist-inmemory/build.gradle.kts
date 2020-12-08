plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("tz.co.asoft.library")
    id("io.codearte.nexus-staging")
    signing
}

android {
    configureAndroid("src/androidMain")
    targetJava("1.8")
}

kotlin {
    universalLib()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":persist-core"))
                api(asoft("paging-core", vers.asoft.paging))
            }
        }
    }
}

aSoftLibrary(
    version = vers.asoft.persist,
    description = "Platform agnostic contracts for persisting data in memomry"
)