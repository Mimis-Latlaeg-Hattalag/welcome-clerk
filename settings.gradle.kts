pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version("1.6.21")
        id("io.gitlab.arturbosch.detekt").version("1.20.0")
        id("org.jetbrains.qodana").version("0.1.13")
        id("com.github.node-gradle.node").version("3.6.0")
    }
}

rootProject.name = "welcome-clerk"

include(":domain:root", ":alien:react-web-client")