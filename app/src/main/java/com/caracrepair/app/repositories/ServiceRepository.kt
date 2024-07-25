package com.caracrepair.app.repositories

import com.caracrepair.app.models.body.BookingServiceBody
import com.caracrepair.app.models.body.ServiceTimesBody
import com.caracrepair.app.models.response.BookingHistoryResponse
import com.caracrepair.app.models.response.BookingServiceResponse
import com.caracrepair.app.models.response.DataResponse
import com.caracrepair.app.models.response.ServiceTimeResponse
import com.caracrepair.app.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ServiceRepository @Inject constructor(
    private val apiService: ApiService
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun bookingService(request: BookingServiceBody): DataResponse<BookingServiceResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.bookingService(request)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getServiceTimes(serviceTimesBody: ServiceTimesBody): DataResponse<List<ServiceTimeResponse>>? {
        return withContext(coroutineContext) {
            try {
                apiService.getServiceTimes(serviceTimesBody)
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getBookingHistory(): DataResponse<List<BookingHistoryResponse>>? {
        return withContext(coroutineContext) {
            try {
                apiService.getBookingHistory()
            } catch (e: Exception) {
                null
            }
        }
    }
}