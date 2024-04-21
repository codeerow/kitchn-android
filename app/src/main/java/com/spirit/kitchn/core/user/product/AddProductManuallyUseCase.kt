package com.spirit.kitchn.core.user.product

import android.content.Context
import android.net.Uri
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import java.io.ByteArrayOutputStream
import java.io.InputStream


class AddProductManuallyUseCase(
    private val httpClient: HttpClient,
    private val context: Context,
) {

    suspend fun execute(
        barcode: String,
        uris: List<Uri>,
        name: String,
        productFamily: String,
    ) {
        val productFamilies = productFamily.split(",")
        httpClient.post("product") {
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
                            append(HttpHeaders.ContentDisposition, "filename=${barcode}_${index}.jpg")
                        })
                    }
                }
            ))
        }
    }

    private fun getBytes(inputStream: InputStream): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }
}