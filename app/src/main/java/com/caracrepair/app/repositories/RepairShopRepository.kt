package com.caracrepair.app.repositories

import com.caracrepair.app.models.responsemodel.DataResponse
import com.caracrepair.app.models.responsemodel.RepairShopResponse
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
}