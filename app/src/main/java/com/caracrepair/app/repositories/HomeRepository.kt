package com.caracrepair.app.repositories

import com.caracrepair.app.models.responsemodel.DataResponse
import com.caracrepair.app.models.responsemodel.HomePageResponse
import com.caracrepair.app.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class HomeRepository @Inject constructor(
    private val apiService: ApiService
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun getHomePage(): DataResponse<HomePageResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.getHomePage()
            } catch (e: Exception) {
                null
            }
        }
    }
}