package com.spirit.kitchn.core.user

import com.spirit.kitchn.core.user.model.UserState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GetUserInfoUseCase(
    private val httpClient: HttpClient,
) {
    suspend fun execute(): UserState {
        return httpClient.get("/user").body()
    }
}