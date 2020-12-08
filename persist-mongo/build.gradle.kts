plugins {
    kotlin("jvm")
    id("tz.co.asoft.library")
}

kotlin {
    target {
        targetJava("1.8")
    }
    sourceSets {
        val main by getting {
            dependencies {
                api(project(":persist-core"))
                api(asoft("paging-core", vers.asoft.paging))
                api("org.jetbrains.kotlinx:kotlinx-serialization-json:${vers.kotlinx.serialization}")
                api("org.mongodb:mongo-java-driver:${vers.mongo}")
            }
        }
    }
}