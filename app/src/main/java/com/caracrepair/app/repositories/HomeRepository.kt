package com.caracrepair.app.repositories

import com.caracrepair.app.models.response.DataResponse
import com.caracrepair.app.models.response.HomePageResponse
import com.caracrepair.app.network.ApiService
import com.caracrepair.app.utils.ApiResponseUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiResponseUtil: ApiResponseUtil
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun getHomePage(): DataResponse<HomePageResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.getHomePage()
            } catch (error: HttpException) {
                apiResponseUtil.getErrorResponse(error)
            } catch (error: Exception) {
                null
            }
        }
    }
}