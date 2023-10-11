/*
MIT License
Copyright (c) 2022 R!dd13r (rdd13r) - Vadim Kuhay
See LICENSE
 */
package me.rdd13.th15.plugins

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfigureRoutingTest {

    @Test
    fun testGet() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello, I'm a 'Welcome' clerk, and my name is Välkommen!", bodyAsText())
        }
    }
}
