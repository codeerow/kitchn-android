package com.spirit.kitchn.core.recipe

import android.content.Context
import android.net.Uri
import com.spirit.kitchn.core.recipe.datasource.RecipeDataSource
import io.ktor.client.HttpClient
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.io.ByteArrayOutputStream
import java.io.InputStream

class CreateRecipeUseCase(
    private val dataSource: RecipeDataSource,
    private val httpClient: HttpClient,
    private val context: Context,
) {
    suspend fun execute(request: Request) = with(request) {
//        httpClient.post("recipe") {
//            contentType(ContentType.Application.Json)
//            setBody(MultiPartFormDataContent(
//                formData {
//                    append("name", name)
//                    append("description", description)
//                    previewImage?.let { uri ->
//                        val iStream = context.contentResolver.openInputStream(uri)
//                        val inputData: ByteArray? = iStream?.let { getBytes(it) }
//                        append("preview", inputData!!, Headers.build {
//                            append(HttpHeaders.ContentType, "image/png")
//                            append(
//                                HttpHeaders.ContentDisposition,
//                                "filename=preview.jpg"
//                            )
//                        })
//                    }
//                    append("steps", Json.encodeToString(steps))
//                }
//            ))
//        }
//        dataSource.refresh()
    }

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

    @Serializable
    data class Request(
        val recipeMainInfo: RecipeMainInfo = RecipeMainInfo(),
        val steps: MutableList<Step> = mutableListOf(),
    ) {

        @Serializable
        data class RecipeMainInfo(
            var name: String = "",
            var description: String = "",

            @Transient
            var previewImage: Uri? = null,
        )

        @Serializable
        data class Step(
            var description: String = "",
            var ingredients: List<String> = listOf(), // TODO: rename to ingredients both here and on the server

            @Transient
            var preview: Uri? = null,
        )
    }
}