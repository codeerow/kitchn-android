package com.spirit.kitchn.core.auth

import com.spirit.kitchn.core.auth.api.AuthAPI
import com.spirit.kitchn.core.auth.repo.TokenRepo
import com.spirit.kitchn.infrastructure.network.executeWith
import io.ktor.client.HttpClient

class LoginUseCase(
    private val httpClient: HttpClient,
    private val tokenRepo: TokenRepo,
) {

    suspend fun execute(email: String, password: String) {
        val endpoint = AuthAPI.LoginEndpoint(
            email = email,
            password = password
        )

        val responseBody = endpoint.executeWith(httpClient)
        tokenRepo.accessToken = responseBody.accessToken
    }
}