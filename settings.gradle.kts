pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
}

rootProject.name = "persist"

include(":persist-core")
include(":persist-client")
include(":persist-keyvalue-core")
project(":persist-keyvalue-core").projectDir = File("persist-keyvalue/persist-keyvalue-core")
include(":persist-keyvalue-locally")
project(":persist-keyvalue-locally").projectDir = File("persist-keyvalue/persist-keyvalue-locally")
include(":persist-keyvalue-inmemory")
project(":persist-keyvalue-inmemory").projectDir = File("persist-keyvalue/persist-keyvalue-inmemory")
include(":persist-inmemory")
include(":persist-mongo")