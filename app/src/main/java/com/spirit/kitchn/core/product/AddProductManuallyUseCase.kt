package com.spirit.kitchn.core.product

import android.content.Context
import android.net.Uri
import com.spirit.kitchn.core.product.api.ProductAPI
import com.spirit.kitchn.infrastructure.network.executeWith
import io.ktor.client.HttpClient


class AddProductManuallyUseCase(
    private val httpClient: HttpClient,
    private val context: Context,
) {

    suspend fun execute(request: Request) = with(request) {
        ProductAPI.AddProductManuallyEndpoint(
            name = name,
            productFamilies = productFamily.split(","),
            barcode = barcode,
            uris = uris,
            context = context,
        ).executeWith(httpClient)
    }

    data class Request(
        val barcode: String,
        val uris: List<Uri>,
        val name: String,
        val productFamily: String,
    )
}