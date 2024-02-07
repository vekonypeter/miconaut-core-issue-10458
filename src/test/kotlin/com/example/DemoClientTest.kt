package com.example

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import org.mockserver.integration.ClientAndServer
import org.mockserver.integration.ClientAndServer.startClientAndServer
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.HttpResponse.response
import org.mockserver.model.MediaType.APPLICATION_JSON

@Property(name = "external.url", value = "http://localhost:1080")
@MicronautTest(startApplication = false)
class DemoClientTest(private val demoClient: DemoClient) : StringSpec({

    lateinit var mockServer: ClientAndServer

    beforeSpec {
        mockServer = startClientAndServer(1080)
    }

    afterSpec {
        mockServer.stop()
    }

    beforeEach {
        mockServer.reset()
    }

    "error is parsed when status is 2xx" {
        val req = request()
            .withMethod("POST")
            .withPath("api/example/entity")

        mockServer.`when`(req).respond(
            response()
                .withStatusCode(200)
                .withBody(
                    """{"error": "something went wrong"}""", APPLICATION_JSON
                )
        )

        shouldThrow<ExternalException> {
            demoClient.getEntity("demo")
        }
    }

    "error is parsed when status is 4xx" {
        val req = request()
            .withMethod("POST")
            .withPath("api/example/entity")

        mockServer.`when`(req).respond(
            response()
                .withStatusCode(403)
                .withBody(
                    """{"error": "something went wrong"}""", APPLICATION_JSON
                )
        )

        shouldThrow<ExternalException> {
            demoClient.getEntity("demo")
        }
    }

    "error is parsed when status is 5xx" {
        val req = request()
            .withMethod("POST")
            .withPath("api/example/entity")

        mockServer.`when`(req).respond(
            response()
                .withStatusCode(501)
                .withBody(
                    """{"error": "something went wrong"}""", APPLICATION_JSON
                )
        )

        shouldThrow<ExternalException> {
            demoClient.getEntity("demo")
        }
    }
})
