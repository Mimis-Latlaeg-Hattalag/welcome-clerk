allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    kotlin("multiplatform") apply false
    id("io.gitlab.arturbosch.detekt") apply false
    id("org.jetbrains.qodana") apply false
    id("com.github.node-gradle.node") apply false
}

defaultTasks("clean", "yarn_cache_clean")