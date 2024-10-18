import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT
import org.gradle.api.tasks.testing.logging.TestLogEvent.STARTED
import org.slf4j.LoggerFactory

private val log by lazy { LoggerFactory.getLogger("me.rdd13.th15.utils") }

// Global kit versions
val useJavaVer: String by project
val kotlinVersion: String by project

// Logging versions
val slf4jVersion: String by project
val kotlinLoggingVersion: String by project
val logbackClassicVersion: String by project

// Validation versions
val jupiterVersion: String by project
val kotestVersion: String by project
val assertjVersion: String by project
val jacocoToolVersion: String by project

// KMP versions
val kotlinxHtmlVersion: String by project
val exposedVersion: String by project
val h2Version: String by project
val flaxoosExtrasVersion: String by project
val webJarsVersion: String by project

// KMP resources
val jssJvmWrappersVersion: String by project
val urlKotlinJsWrappers: String by project
val urlConfluentIO: String by project

val companionMainClass: String by project

repositories {
    mavenCentral()
    maven { url = uri(urlKotlinJsWrappers) }
    maven { url = uri(urlConfluentIO) }
}

plugins {
    kotlin("jvm")

    id("org.jetbrains.kotlinx.kover")
    jacoco

    id("io.gitlab.arturbosch.detekt") apply false

    id("io.ktor.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")

    id("org.jetbrains.dokka")
}

application {
    mainClass.set(companionMainClass)

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
    if (isDevelopment) log.warn("\n\nCompanion application is built in development mode!\n\n")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(useJavaVer)
    }
}

dependencies {
    implementation(platform(kotlin("bom")))
    testImplementation(platform("org.junit:junit-bom:$jupiterVersion"))
    testImplementation(platform("io.kotest:kotest-bom:$kotestVersion"))
    testImplementation(platform("org.assertj:assertj-bom:$assertjVersion"))

    api("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("io.github.oshai:kotlin-logging:$kotlinLoggingVersion")
    implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")

    implementation("org.jetbrains.kotlin-wrappers:kotlin-css-jvm:$jssJvmWrappersVersion")

    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-auto-head-response-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-webjars-jvm")
    implementation("io.ktor:ktor-server-resources-jvm")
    implementation("io.ktor:ktor-server-host-common-jvm")
    implementation("io.ktor:ktor-server-status-pages-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-openapi")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-call-id-jvm")
    implementation("io.ktor:ktor-server-metrics-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-html-builder-jvm")

    implementation("org.webjars:jquery:$webJarsVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.h2database:h2:$h2Version")

    testImplementation(kotlin("test-junit"))

    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-params")

    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest:kotest-property")

    testImplementation("io.kotest:kotest-extensions-junitxml")
    testImplementation("io.kotest:kotest-extensions-htmlreporter")

    testImplementation("io.ktor:ktor-server-test-host-jvm")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()

    testLogging {
        showStandardStreams = true
        showStackTraces = true
        exceptionFormat = FULL
        events = setOf(STARTED, PASSED, SKIPPED, FAILED, STANDARD_OUT, STANDARD_ERROR)
    }
    systemProperty("kotest.framework.dump.config", true)
    systemProperty("gradle.build.dir", layout.buildDirectory)
    systemProperties = System.getProperties().asIterable().associate { it.key.toString() to it.value }

    finalizedBy(tasks.jacocoTestReport)

    reports {
        html.required.set(true)
        junitXml.required.set(true)
    }
}

jacoco {
    toolVersion = jacocoToolVersion
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
    finalizedBy(tasks.jacocoTestCoverageVerification)
}

tasks.withType<JacocoCoverageVerification> {
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }
        rule {
            enabled = false
            element = "CLASS"
            includes = listOf("me.riddle.*")
            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                minimum = "0.4".toBigDecimal()
            }
        }
    }
}
