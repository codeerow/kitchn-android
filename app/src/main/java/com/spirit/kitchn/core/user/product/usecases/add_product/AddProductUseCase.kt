package com.spirit.kitchn.core.user.product.usecases.add_product

import android.content.Context
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.spirit.kitchn.core.user.product.datasource.UserProductDataSource
import com.spirit.kitchn.core.user.product.usecases.add_product.model.AddProductRequestBody
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AddProductUseCase(
    private val scanner: GmsBarcodeScanner,
    private val dataSource: UserProductDataSource,
    private val httpClient: HttpClient,
    private val context: Context,
) {

    suspend fun execute(): Result {
        val barcode = scanProductBarcode() ?: return Result.Failure.ScanFailed
        val requestBody = AddProductRequestBody(barcode = barcode)

        val response = httpClient.post("/user/products") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
        return when (response.status) {
            HttpStatusCode.NotFound -> Result.Failure.ProductNotFound(barcode = barcode)
            else -> {
                dataSource.refresh()
                Result.Success
            }
        }
    }

    private suspend fun scanProductBarcode(): String? {
        return suspendCoroutine { continuation ->
            val moduleInstall = ModuleInstall.getClient(context)
            val moduleInstallRequest = ModuleInstallRequest.newBuilder()
                .addApi(GmsBarcodeScanning.getClient(context))
                .build()
            moduleInstall
                .installModules(moduleInstallRequest)
                .addOnSuccessListener {
                    if (it.areModulesAlreadyInstalled()) {
                        scanner.startScan().addOnSuccessListener { barcode ->
                            continuation.resume(getDetails(barcode))
                        }.addOnFailureListener { error ->
                            continuation.resumeWithException(error)
                        }
                    }
                }
                .addOnFailureListener { error ->
                    continuation.resumeWithException(error)
                }
        }
    }

    private fun getDetails(barcode: Barcode): String? {
        return when (barcode.valueType) {
            Barcode.TYPE_PRODUCT -> barcode.displayValue
            else -> null
        }
    }

    sealed interface Result {
        data object Success : Result
        sealed interface Failure : Result {
            data class ProductNotFound(val barcode: String) : Failure
            data object ScanFailed : Failure
        }
    }
}