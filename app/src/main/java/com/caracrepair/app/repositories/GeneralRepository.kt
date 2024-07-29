package com.caracrepair.app.repositories

import com.caracrepair.app.network.ApiService
import com.caracrepair.app.utils.ApiResponseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class GeneralRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiResponseUtil: ApiResponseUtil
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun uploadImage(imageFile: File): String? {
        return withContext(coroutineContext) {
            try {
                val fileUpload = MultipartBody.Part.createFormData("file", imageFile.name,  imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull()))
                apiService.uploadImage(fileUpload).data?.imageUrl
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            } catch (error: Exception) {
                null
            }
        }
    }
}