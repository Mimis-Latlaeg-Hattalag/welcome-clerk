pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform").version("1.9.10")
        id("io.gitlab.arturbosch.detekt").version("1.20.0")
        id("org.jetbrains.qodana").version("0.1.13")
        id("com.github.node-gradle.node").version("7.0.1")
    }
}

rootProject.name = "welcome-clerk"

include(":domain:root", ":alien:react-web-client")