package com.spirit.kitchn.core.product.api

import android.content.Context
import android.net.Uri
import com.spirit.kitchn.core.product.model.ProductDTO
import com.spirit.kitchn.infrastructure.network.Endpoint
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import java.io.ByteArrayOutputStream
import java.io.InputStream

sealed interface ProductAPI {
    data class GetProductDetailsEndpoint(
        private val id: String,
    ) : ProductAPI, Endpoint<ProductDTO>(
        urlString = "/product/${id}",
        {
            method = HttpMethod.Get
        }
    )

    data class AddProductManuallyEndpoint(
        private val name: String,
        private val barcode: String,
        private val productFamilies: List<String>,
        private val uris: List<Uri>,
        private val context: Context,
    ) : ProductAPI, Endpoint<ProductDTO>(
        urlString = "product",
        {
            contentType(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(
                formData {
                    append("name", name)
                    append("barcode", barcode)
                    productFamilies.forEach { append("productFamilyIds", it) }
                    uris.forEachIndexed { index, uri ->
                        val iStream = context.contentResolver.openInputStream(uri)
                        val inputData: ByteArray? = iStream?.let { getBytes(it) }
                        append("images", inputData!!, Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${barcode}_${index}.jpg"
                            )
                        })
                    }
                }
            ))
            method = HttpMethod.Post
        })
}



fun getBytes(inputStream: InputStream): ByteArray {
    val byteBuffer = ByteArrayOutputStream()
    val bufferSize = 1024
    val buffer = ByteArray(bufferSize)
    var len: Int
    while (inputStream.read(buffer).also { len = it } != -1) {
        byteBuffer.write(buffer, 0, len)
    }
    return byteBuffer.toByteArray()
}