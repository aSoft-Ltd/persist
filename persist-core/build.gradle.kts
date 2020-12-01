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
                api("org.jetbrains.kotlinx:atomicfu:${vers.kotlinx.atomicfu}")
//                api(project(":paging-core"))
            }
        }
    }
}