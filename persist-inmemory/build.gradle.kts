plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("tz.co.asoft.library")
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