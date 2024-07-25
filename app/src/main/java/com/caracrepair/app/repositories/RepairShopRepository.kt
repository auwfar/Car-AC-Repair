package com.caracrepair.app.repositories

import com.caracrepair.app.models.response.DataResponse
import com.caracrepair.app.models.response.RepairShopDetailResponse
import com.caracrepair.app.models.response.RepairShopResponse
import com.caracrepair.app.network.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RepairShopRepository @Inject constructor(
    private val apiService: ApiService
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun getRepairShops(): DataResponse<List<RepairShopResponse>>? {
        return withContext(coroutineContext) {
            try {
                apiService.getRepairShops()
            } catch (e: Exception) {
                null
            }
        }
    }

    suspend fun getRepairShopDetail(repairShopId: Int): DataResponse<RepairShopDetailResponse>? {
        return withContext(coroutineContext) {
            try {
                apiService.getRepairShopDetail(repairShopId)
            } catch (e: Exception) {
                null
            }
        }
    }
}