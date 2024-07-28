package com.caracrepair.app.repositories

import android.net.Uri
import androidx.core.net.toFile
import com.caracrepair.app.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GeneralRepository @Inject constructor(
    private val apiService: ApiService,
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun uploadImage(imageUri: Uri): String? {
        return withContext(coroutineContext) {
            try {
                val file = imageUri.toFile()
                val fileUpload = MultipartBody.Part.createFormData("file", file.name,  file.asRequestBody("image/jpeg".toMediaTypeOrNull()))
                apiService.uploadImage(fileUpload).data?.imageUrl
            } catch (error: Exception) {
                null
            }
        }
    }
}