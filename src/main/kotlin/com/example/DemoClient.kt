package com.example

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.serde.annotation.Serdeable
import java.util.*

@DemoErrorHandling
@Client("\${external.url}")
interface DemoClient {

    @Post("api/example/entity")
    suspend fun getEntity(entityId: String): DemoResponse<Entity>
}

@Introspected
@Serdeable.Deserializable
data class DemoResponse<T>(val result: T)

@Introspected
@Serdeable.Deserializable
data class Entity(
    val entityId: String,
    val created: Long,
)

@Introspected
@Serdeable.Deserializable
data class DemoErrorResponse(
    val error: String
)

