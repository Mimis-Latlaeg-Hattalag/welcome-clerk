import io.gitlab.arturbosch.detekt.Detekt
import org.asciidoctor.gradle.jvm.AbstractAsciidoctorTask.JAVA_EXEC
import org.asciidoctor.gradle.jvm.AsciidoctorTask
import org.asciidoctor.gradle.jvm.epub.AsciidoctorEpubTask
import org.asciidoctor.gradle.jvm.epub.AsciidoctorEpubTask.EPUB3
import org.asciidoctor.gradle.jvm.pdf.AsciidoctorPdfTask
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import org.slf4j.LoggerFactory

private val log by lazy { LoggerFactory.getLogger("me.rdd13.th15") }

log.warn(
    """

    |===============================================================================|
    |                                                                               |
    |               Welcome to Multiplatform Kotlin DDD Archetypes.                 |
    |                                                                               |
    | Please refer to https://github.com/rdd13r/welcome-clerk for more information. |
    |                                                                               |
    |===============================================================================|


    """.trimIndent()
)

val useJavaVer: String by project
val jacocoToolVersion: String by project
val asciidoctorJDiagramVersion: String by project

val adocJvmParams = listOf(
    "--add-opens",
    "java.base/sun.nio.ch=ALL-UNNAMED",
    "--add-opens",
    "java.base/java.io=ALL-UNNAMED"
)

plugins {
    base

    id("org.jetbrains.kotlinx.kover") apply false
    jacoco

    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.qodana")

    // Covers all polyglot variants
    kotlin("multiplatform") apply false

    id("io.ktor.plugin") apply false
    id("org.jetbrains.kotlin.plugin.serialization") apply false

    id("org.jetbrains.dokka") apply false
    id("org.asciidoctor.jvm.pdf")
    id("org.asciidoctor.jvm.gems")
    id("org.asciidoctor.jvm.epub")
    id("org.asciidoctor.jvm.convert")

    id("com.avast.gradle.docker-compose")

    id("com.github.node-gradle.node") apply false
}

tasks.withType<Detekt>().configureEach {
    parallel = true
    debug = true
    ignoreFailures = true

    config.setFrom(file("config/detekt/detekt.yml"))
    baseline = file("config/detekt/baseline.xml")

    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("build/reports/detekt.xml"))
        html.required.set(true)
        html.outputLocation.set(file("build/reports/detekt.html"))
        // Enable/Disable TXT report (default: true)
        txt.required.set(true)
        txt.outputLocation.set(file("build/reports/detekt.txt"))
        // Enable/Disable SARIF report (default: false)
        sarif.required.set(true)
        sarif.outputLocation.set(file("build/reports/detekt.sarif"))
        // Enable/Disable MD report (default: false)
        md.required.set(true)
        md.outputLocation.set(file("build/reports/detekt.md"))
    }
}

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version.set("1.3.1")
    enableExperimentalRules.set(true)
    ignoreFailures.set(true)
    debug.set(true)
    verbose.set(true)
    outputToConsole.set(true)
    coloredOutput.set(true)
    reporters {
        reporter(ReporterType.CHECKSTYLE)
        reporter(ReporterType.JSON)
        reporter(ReporterType.HTML)
    }
}

jacoco {
    toolVersion = jacocoToolVersion
}

listOf(
    tasks.withType<AsciidoctorTask>(),
    tasks.withType<AsciidoctorPdfTask>(),
    tasks.withType<AsciidoctorEpubTask>()
)
    .forEach {
        it.configureEach {
            notCompatibleWithConfigurationCache("Asciidoctor is not compatible with configuration cache.")
            setExecutionMode(JAVA_EXEC)
            jvm { jvmArgs(adocJvmParams) }

            isLogDocuments = true
            baseDirFollowsSourceDir()
            setSourceDir(file("."))
            sources(
                delegateClosureOf<PatternSet> {
                    include(
                        "index.adoc",
                        "README.adoc",
                        "docs/SpringBootDataRestAggregate.adoc"
                    )
                }
            )
        }
    }

tasks.withType<AsciidoctorEpubTask>().configureEach { ebookFormats(EPUB3) }

asciidoctorj {
    modules {
        diagram.use()
        diagram.setVersion(asciidoctorJDiagramVersion)
    }
    options["diagram-server-url"] = "http://localhost:8000"
    options["diagram-server-type"] = "kroki_io"
}

dockerCompose {
    useComposeFiles = listOf("kroki/docker-compose.yml")
    isRequiredBy(tasks.asciidoctor)
    isRequiredBy(tasks.asciidoctorPdf)
}

defaultTasks("clean", "build", "asciidoctor", "asciidoctorPdf", "asciidoctorEpub")
