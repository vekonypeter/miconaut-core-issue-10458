package com.example

import io.micronaut.http.annotation.FilterMatcher
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.CLASS

@FilterMatcher
@MustBeDocumented
@Retention(RUNTIME)
@Target(CLASS)
annotation class DemoErrorHandling
