package com.spirit.kitchn.core.recipe

import android.content.Context
import android.net.Uri
import com.spirit.kitchn.core.recipe.datasource.RecipeDataSource
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
import kotlinx.serialization.Serializable

class CreateRecipeUseCase(
    private val dataSource: RecipeDataSource,
    private val httpClient: HttpClient,
    private val context: Context,
) {
    suspend fun execute(request: Request) = with(request) {
        httpClient.post("recipe") {
            contentType(ContentType.Application.Json)
            setBody(MultiPartFormDataContent(
                formData {
                    append("name", name)
                    append("description", description)
                    previewImage?.let {
                        val iStream = context.contentResolver.openInputStream(previewImage)
                        val inputData: ByteArray? = iStream?.let { getBytes(it) }
                        append("preview", inputData!!, Headers.build {
                            append(HttpHeaders.ContentType, "image/png")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=preview.jpg"
                            )
                        })
                    }
                }
            ))
        }
        dataSource.refresh()
    }

    @Serializable
    data class RecipeRequestBody(
        val title: String,
        val description: String,
    )

    private fun getBytes(inputStream: InputStream): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    data class Request(
        val name: String,
        val previewImage: Uri?,
        val description: String,
    )
}