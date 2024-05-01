package com.spirit.kitchn.core.recipe

import com.spirit.kitchn.core.user.personal_info.model.PersonalInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class GetAllRecipesUseCase(
    private val httpClient: HttpClient,
) {
    suspend fun execute(): PersonalInfo {
        return httpClient.get("/user").body()
    }
}