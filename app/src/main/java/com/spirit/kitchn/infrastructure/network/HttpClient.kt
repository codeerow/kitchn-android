package com.spirit.kitchn.infrastructure.network

import android.util.Log
import androidx.navigation.NavHostController
import com.spirit.kitchn.BuildConfig
import com.spirit.kitchn.core.auth.repo.TokenRepo
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun buildHttpClient(
    tokenRepo: TokenRepo,
    rootNavController: NavHostController,
) = HttpClient(Android) {

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                Log.d("Http Client", message)
            }
        }
    }

    install(ResponseObserver) {
        onResponse { response ->
            println("HTTP status: ${response.status.value}")
            // Add custom logic for response handling
        }
    }

    defaultRequest {
        url {
            protocol = URLProtocol.HTTP
            host = BuildConfig.BASE_URL
        }
    }


//    coroutineScope {
//        withContext(Dispatchers.Main) {
//            val route = ERROR_ROUTE.replace(
//                "{$ERROR_DESCRIPTION_ARG}",
//                "There is no internet connection"
//            )
//            rootNavController.navigate(route)
//        }
//    }

    install(Auth) {
        bearer {
            loadTokens {
                val access = tokenRepo.accessToken
                BearerTokens(access, "")
            }
            refreshTokens {
                val access = tokenRepo.accessToken
                BearerTokens(access, "")
            }
        }
    }
}
