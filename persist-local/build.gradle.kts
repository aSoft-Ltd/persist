plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("tz.co.asoft.library")
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