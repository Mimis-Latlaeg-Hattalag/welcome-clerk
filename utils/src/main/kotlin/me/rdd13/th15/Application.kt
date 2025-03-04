package me.rdd13.th15

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.rdd13.th15.plugins.*

fun main() {
    embeddedServer(Netty, port = 28080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureTemplating()
    configureDatabases()
    configureAdministration()
    configureRouting()
}
