/*
MIT License
Copyright (c) 2022 R!dd13r (rdd13r) - Vadim Kuhay
See LICENSE
 */
package me.rdd13.th15

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import me.rdd13.th15.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}
