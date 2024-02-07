package com.example

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.http.filter.ClientFilterChain
import io.micronaut.http.filter.HttpClientFilter
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

@Singleton
@DemoErrorHandling
class DemoErrorHandler : HttpClientFilter {

    override fun doFilter(request: MutableHttpRequest<*>, chain: ClientFilterChain): Publisher<out HttpResponse<*>> {
        return Flux
            .from(chain.proceed(request))
            .doOnError(HttpClientResponseException::class.java) { ex ->
                ex.response.getBody(DemoErrorResponse::class.java) // This part does not work anymore with Micronaut 4
                    .ifPresent { responseBody ->                   // because ex.response.body is always null
                        throw ExternalException(responseBody)
                    }
                throw ex
            }
    }
}

data class ExternalException(val errorResponse: DemoErrorResponse) : RuntimeException(errorResponse.error)
