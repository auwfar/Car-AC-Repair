package com.caracrepair.app.repositories

import androidx.core.net.toFile
import com.caracrepair.app.models.body.BookingServiceBody
import com.caracrepair.app.models.body.RescheduleServiceBody
import com.caracrepair.app.models.body.ServiceTimesBody
import com.caracrepair.app.models.body.UploadPaymentProofImageBody
import com.caracrepair.app.models.response.BookingHistoryResponse
import com.caracrepair.app.models.response.BookingServiceResponse
import com.caracrepair.app.models.response.DataResponse
import com.caracrepair.app.models.response.RescheduleServiceResponse
import com.caracrepair.app.models.response.ServiceDetailResponse
import com.caracrepair.app.models.response.ServicePaymentResponse
import com.caracrepair.app.models.response.ServiceTimeResponse
import com.caracrepair.app.models.response.StatusResponse
import com.caracrepair.app.network.ApiService
import com.caracrepair.app.utils.ApiResponseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ServiceRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiResponseUtil: ApiResponseUtil
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun bookingService(request: BookingServiceBody): DataResponse<BookingServiceResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.bookingService(request)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            }
        }
    }

    suspend fun getServiceTimes(serviceTimesBody: ServiceTimesBody): DataResponse<List<ServiceTimeResponse>>? {
        return withContext(coroutineContext) {
            try {
                apiService.getServiceTimes(serviceTimesBody)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            }
        }
    }

    suspend fun getBookingHistory(): DataResponse<List<BookingHistoryResponse>>? {
        return withContext(coroutineContext) {
            try {
                apiService.getBookingHistory()
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            }
        }
    }

    suspend fun getServiceDetail(serviceId: Int, userId: Int): DataResponse<ServiceDetailResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.getServiceDetail(serviceId, userId)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            }
        }
    }

    suspend fun rescheduleService(request: RescheduleServiceBody): DataResponse<RescheduleServiceResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.rescheduleService(request)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            }
        }
    }

    suspend fun getServicePayment(serviceId: Int, userId: Int): DataResponse<ServicePaymentResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.getServicePayment(serviceId, userId)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            }
        }
    }

    suspend fun uploadPaymentProofImage(uploadPaymentProofImageBody: UploadPaymentProofImageBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                val serviceId = uploadPaymentProofImageBody.serviceId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val map = mutableMapOf("order_id" to serviceId)
                val file = uploadPaymentProofImageBody.imageUri.toFile()
                val fileUpload = MultipartBody.Part.createFormData("image", file.name,  file.asRequestBody("image/jpeg".toMediaTypeOrNull()))
                apiService.uploadPaymentProofImage(map, fileUpload)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            }
        }
    }
}