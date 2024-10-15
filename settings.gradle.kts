pluginManagement {

    val kotlinVersion: String by extra

    val detektPluginVersion: String by extra
    val embeddedQodanaVersion: String by extra

    val toolchainsFoojayResolverVersion: String by extra


    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version toolchainsFoojayResolverVersion

        kotlin("multiplatform") version kotlinVersion

//        jacoco
//        https://github.com/JLLeitschuh/ktlint-gradle
//        id("org.jlleitschuh.gradle.ktlint") version ktlintVersion
//        id("org.jetbrains.dokka") version dokkaVersion

        id("io.gitlab.arturbosch.detekt") version detektPluginVersion
        id("org.jetbrains.qodana") version embeddedQodanaVersion

//        id("com.avast.gradle.docker-compose") version dockerComposeVersion

        id("com.github.node-gradle.node").version("7.0.1")

//        asciidoctorJvmVersion.apply {
//            id("org.asciidoctor.jvm.pdf") version this
//            id("org.asciidoctor.jvm.gems") version this
//            id("org.asciidoctor.jvm.epub") version this
//            id("org.asciidoctor.jvm.convert") version this
//        }
//
//        id("com.avast.gradle.docker-compose") version dockerComposeVersion

    }
}

rootProject.name = "welcome-clerk"

//include(":domain:root")
