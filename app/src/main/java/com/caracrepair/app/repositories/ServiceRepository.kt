package com.caracrepair.app.repositories

import com.caracrepair.app.models.body.BookingServiceBody
import com.caracrepair.app.models.body.RescheduleServiceBody
import com.caracrepair.app.models.body.UploadPaymentProofImageBody
import com.caracrepair.app.models.response.BookingServiceResponse
import com.caracrepair.app.models.response.DataResponse
import com.caracrepair.app.models.response.ServiceDetailResponse
import com.caracrepair.app.models.response.ServiceResponse
import com.caracrepair.app.models.response.StatusResponse
import com.caracrepair.app.network.ApiService
import com.caracrepair.app.utils.ApiResponseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
            } catch (error: Exception) {
                null
            }
        }
    }

    suspend fun getServiceHistory(userId: String): DataResponse<List<ServiceResponse>>? {
        return withContext(coroutineContext) {
            try {
                apiService.getServiceHistory(userId)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            } catch (error: Exception) {
                null
            }
        }
    }

    suspend fun getServiceDetail(serviceId: String): DataResponse<ServiceDetailResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.getServiceDetail(serviceId)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            } catch (error: Exception) {
                null
            }
        }
    }

    suspend fun rescheduleService(serviceId: String, request: RescheduleServiceBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.rescheduleService(serviceId, request)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            } catch (error: Exception) {
                null
            }
        }
    }

    suspend fun uploadPaymentProofImage(serviceId: String, uploadPaymentProofImageBody: UploadPaymentProofImageBody): StatusResponse? {
        return withContext(coroutineContext) {
            try {
                apiService.uploadPaymentProofImage(serviceId, uploadPaymentProofImageBody)
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            } catch (error: Exception) {
                null
            }
        }
    }
}