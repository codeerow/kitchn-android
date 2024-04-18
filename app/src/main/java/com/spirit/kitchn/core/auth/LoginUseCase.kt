package com.spirit.kitchn.core.auth

import com.spirit.kitchn.core.auth.repo.TokenRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

class LoginUseCase(
    private val httpClient: HttpClient,
    private val tokenRepo: TokenRepo,
) {

    suspend fun execute(email: String, password: String) {
        val requestBody = LoginRequestBody(email = email, password = password)

        val responseBody: LoginResponseBody = httpClient.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()

        tokenRepo.accessToken = responseBody.accessToken
    }


    @Serializable
    private data class LoginRequestBody(val email: String, val password: String)

    @Serializable
    private data class LoginResponseBody(val accessToken: String)
}