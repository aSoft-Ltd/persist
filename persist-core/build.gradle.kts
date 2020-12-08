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
                api(asoft("paging-core", vers.asoft.paging))
            }
        }

        val commonTest by getting {
            dependencies {
                api(asoft("test", vers.asoft.test))
            }
        }
    }
}