package com.spirit.kitchn.infrastructure.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request

open class Endpoint<O>(
    val urlString: String,
    val block: HttpRequestBuilder.() -> Unit = {},
)

suspend inline fun <reified O> Endpoint<O>.executeWith(client: HttpClient): O {
    return client.request(urlString, block).body()
}