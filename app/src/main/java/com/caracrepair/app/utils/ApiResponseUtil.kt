package com.caracrepair.app.utils

import androidx.lifecycle.MutableLiveData
import com.caracrepair.app.consts.StringConst
import com.caracrepair.app.models.response.DataResponse
import com.caracrepair.app.models.response.StatusResponse
import retrofit2.HttpException
import javax.inject.Inject

class ApiResponseUtil @Inject constructor(val gsonUtil: GsonUtil) {
    inline fun <reified T> getErrorResponse(error: HttpException): T? {
        return try {
            gsonUtil.fromJson<T>(error.response()?.errorBody()?.string())
        } catch (e: Exception) {
            null
        }
    }

    fun setResponseListener(
        response: StatusResponse?,
        errorMessageLiveData: MutableLiveData<String>,
        responseListener: ResponseListener
    ) {
        when {
            response?.status == true -> {
                responseListener.onSuccess()
            }
            response?.status != true -> {
                errorMessageLiveData.postValue(response?.message.orEmpty())
                responseListener.onFailure()
            }
            else -> {
                errorMessageLiveData.postValue(StringConst.GENERAL_ERROR_MESSAGE)
                responseListener.onFailure()
            }
        }
    }

    fun setResponseListener(
        response: DataResponse<*>?,
        errorMessageLiveData: MutableLiveData<String>,
        responseListener: ResponseListener
    ) {
        when {
            response?.status == true -> {
                responseListener.onSuccess()
            }
            response?.status != true -> {
                errorMessageLiveData.postValue(response?.message.orEmpty())
                responseListener.onFailure()
            }
            else -> {
                errorMessageLiveData.postValue(StringConst.GENERAL_ERROR_MESSAGE)
                responseListener.onFailure()
            }
        }
    }

    interface ResponseListener {
        fun onSuccess()
        fun onFailure() {}
    }
}