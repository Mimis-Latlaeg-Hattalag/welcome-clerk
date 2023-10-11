/*
MIT License
Copyright (c) 2022 R!dd13r (rdd13r) - Vadim Kuhay
See LICENSE
 */
package me.rdd13.th15

import io.ktor.server.testing.testApplication
import me.rdd13.th15.plugins.configureRouting
import kotlin.test.Test

class ApplicationTest {

    @Test
    fun testRouteSet() = testApplication {
        application {
            configureRouting()
        }
    }
}
