/*
MIT License
Copyright (c) 2022 R!dd13r (rdd13r) - Vadim Kuhay
See LICENSE
 */
package me.rdd13.th15.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.log
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {
        trace { application.log.trace(it.buildText()) }
        get("/") {
            call.respondText("Hello, I'm a 'Welcome' clerk, and my name is Välkommen!")
        }
    }

    routing {
    }
}
