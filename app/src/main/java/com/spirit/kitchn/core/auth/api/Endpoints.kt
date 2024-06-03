package com.spirit.kitchn.core.auth.api

import com.spirit.kitchn.infrastructure.network.Endpoint
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

sealed interface AuthAPI {
    data class LoginEndpoint(
        private val email: String,
        private val password: String,
    ) : AuthAPI, Endpoint<LoginEndpoint.ResponseBody>(
        urlString = "/auth/login",
        {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(RequestBody(email = email, password = password))
        }
    ) {
        @Serializable
        private data class RequestBody(val email: String, val password: String)

        @Serializable
        data class ResponseBody(val accessToken: String)
    }
}