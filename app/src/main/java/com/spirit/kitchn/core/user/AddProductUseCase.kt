package com.spirit.kitchn.core.user

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.spirit.kitchn.core.user.datasource.ProductDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlinx.serialization.Serializable

class AddProductUseCase(
    private val scanner: GmsBarcodeScanner,
    private val dataSource: ProductDataSource,
    private val httpClient: HttpClient,
) {

    suspend fun execute(): Result {
        val scanResult: String = suspendCoroutine { continuation ->
            scanner.startScan().addOnSuccessListener {
                continuation.resume(getDetails(it))
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
        } ?: return Result.Failure.ScanFailed

        val requestBody = AddProductRequestBody(barcode = scanResult)

        val response = httpClient.post("/user/products") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
        return when (response.status) {
            HttpStatusCode.NotFound -> Result.Failure.ProductNotFound(barcode = scanResult)
            else -> {
                dataSource.refresh()
                Result.Success
            }
        }
    }

    private fun getDetails(barcode: Barcode): String? {
        return when (barcode.valueType) {
            Barcode.TYPE_PRODUCT -> barcode.displayValue
            else -> null
        }
    }

    @Serializable
    private data class AddProductRequestBody(val barcode: String)

    sealed interface Result {
        data object Success : Result
        sealed interface Failure : Result {
            data class ProductNotFound(val barcode: String) : Failure
            data object ScanFailed : Failure
        }
    }
}