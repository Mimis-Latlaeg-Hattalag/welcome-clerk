@file:Suppress("UnstableApiUsage")

// Forward-looking configuration using well-tested conventions -- upgrade regularly!

rootProject.name = "welcome-clerk"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include("utils")

pluginManagement {

    val dockerComposeVersion: String by extra

    val kotlinVersion: String by extra

    val detektPluginVersion: String by extra
    val embeddedQodanaVersion: String by extra
    val koverVersion: String by extra
    val ktLintVersion: String by extra

    val toolchainsFoojayResolverVersion: String by extra
    val nodePluginVersion: String by extra

    val ktorVersion: String by extra
    val kotlinSerializationVersion: String by extra

    val dokkaVersion: String by extra
    val asciidoctorJvmVersion: String by extra

    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.avast.gradle.docker-compose") version dockerComposeVersion

        // https://plugins.gradle.org/plugin/org.gradle.toolchains.foojay-resolver-convention
        id("org.gradle.toolchains.foojay-resolver-convention") version toolchainsFoojayResolverVersion

        kotlin("multiplatform") version kotlinVersion

        // https://github.com/JLLeitschuh/ktlint-gradle
        // https://github.com/pinterest/ktlint
        id("org.jlleitschuh.gradle.ktlint") version ktLintVersion
        id("org.jetbrains.kotlinx.kover") version koverVersion
        jacoco

        id("io.gitlab.arturbosch.detekt") version detektPluginVersion
        id("org.jetbrains.qodana") version embeddedQodanaVersion

        id("io.ktor.plugin") version ktorVersion
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinSerializationVersion

        id("org.jetbrains.dokka") version dokkaVersion

        id("com.github.node-gradle.node") version nodePluginVersion

        asciidoctorJvmVersion.apply {
            id("org.asciidoctor.jvm.pdf") version this
            id("org.asciidoctor.jvm.gems") version this
            id("org.asciidoctor.jvm.epub") version this
            id("org.asciidoctor.jvm.convert") version this
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}
