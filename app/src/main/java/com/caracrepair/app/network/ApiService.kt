package com.caracrepair.app.network

import com.caracrepair.app.models.body.AddAddressBody
import com.caracrepair.app.models.body.AddCarBody
import com.caracrepair.app.models.body.BookingServiceBody
import com.caracrepair.app.models.body.ChangePasswordBody
import com.caracrepair.app.models.body.ChangeProfileBody
import com.caracrepair.app.models.body.RequestOtpBody
import com.caracrepair.app.models.body.RescheduleServiceBody
import com.caracrepair.app.models.body.ResetPasswordBody
import com.caracrepair.app.models.body.SignInBody
import com.caracrepair.app.models.body.SignUpBody
import com.caracrepair.app.models.body.UpdateAddressBody
import com.caracrepair.app.models.body.UpdateCarBody
import com.caracrepair.app.models.body.UploadPaymentProofImageBody
import com.caracrepair.app.models.body.VerifyOtpBody
import com.caracrepair.app.models.response.AddressResponse
import com.caracrepair.app.models.response.BookingServiceResponse
import com.caracrepair.app.models.response.CarResponse
import com.caracrepair.app.models.response.DataResponse
import com.caracrepair.app.models.response.HomePageResponse
import com.caracrepair.app.models.response.RepairShopDetailResponse
import com.caracrepair.app.models.response.RepairShopResponse
import com.caracrepair.app.models.response.ServiceDetailResponse
import com.caracrepair.app.models.response.ServiceResponse
import com.caracrepair.app.models.response.SignInResponse
import com.caracrepair.app.models.response.StatusResponse
import com.caracrepair.app.models.response.UploadImageResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/auth/login")
    suspend fun signIn(@Body request: SignInBody): DataResponse<SignInResponse>

    @POST("api/auth/register")
    suspend fun signUp(@Body request: SignUpBody): StatusResponse

    @POST("api/auth/request-otp")
    suspend fun requestOtp(@Body request: RequestOtpBody): StatusResponse

    @POST("api/auth/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyOtpBody): StatusResponse

    @POST("api/auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordBody): StatusResponse

    @GET("api/dashboard?platform=mobile")
    suspend fun getHomePage(@Query("customer_id") userId: String): DataResponse<HomePageResponse>

    @GET("api/carshops")
    suspend fun getRepairShops(): DataResponse<List<RepairShopResponse>>

    @GET("api/carshops/{repair_shop_id}")
    suspend fun getRepairShopDetail(@Path("repair_shop_id") repairShopId: String): DataResponse<RepairShopDetailResponse>

    @POST("api/orders")
    suspend fun bookingService(@Body request: BookingServiceBody): DataResponse<BookingServiceResponse>

    @GET("api/orders/customer/{user_id}")
    suspend fun getServiceHistory(@Path("user_id") userId: String): DataResponse<List<ServiceResponse>>

    @GET("api/orders/detail/{order_id}")
    suspend fun getServiceDetail(
        @Path("order_id") serviceId: String
    ): DataResponse<ServiceDetailResponse>

    @PUT("api/orders/{order_id}/customer/reschedule")
    suspend fun rescheduleService(@Path("order_id") serviceId: String, @Body request: RescheduleServiceBody): StatusResponse

    @POST("api/auth/change-password/{user_id}")
    suspend fun changePassword(@Path("user_id") userId: String, @Body request: ChangePasswordBody): StatusResponse

    @GET("api/cars")
    suspend fun getCars(@Query("user_id") userId: String): DataResponse<List<CarResponse>>

    @GET("api/addresses")
    suspend fun getAddresses(@Query("user_id") userId: String): DataResponse<List<AddressResponse>>

    @POST("api/cars")
    suspend fun addCar(@Body request: AddCarBody): StatusResponse

    @PUT("api/cars/{car_id}")
    suspend fun updateCar(@Path("car_id") carId: String, @Body request: UpdateCarBody): StatusResponse

    @DELETE("api/cars/{car_id}")
    suspend fun removeCar(@Path("car_id") carId: String): StatusResponse

    @POST("api/addresses")
    suspend fun addAddress(@Body request: AddAddressBody): StatusResponse

    @PUT("api/addresses/{address_id}")
    suspend fun updateAddress(@Path("address_id") addressId: String, @Body request: UpdateAddressBody): StatusResponse

    @DELETE("api/addresses/{address_id}")
    suspend fun removeAddress(@Path("address_id") addressId: String): StatusResponse

    @PUT("api/orders/{order_id}/customer/choose-payment")
    suspend fun uploadPaymentProofImage(@Path("order_id") serviceId: String, @Body request: UploadPaymentProofImageBody): StatusResponse

    @PUT("api/users/customer/{user_id}")
    suspend fun changeProfile(@Path("user_id") userId: String, @Body request: ChangeProfileBody): StatusResponse

    @Multipart
    @POST("api/utils/upload/image")
    suspend fun uploadImage(@Part file: MultipartBody.Part): DataResponse<UploadImageResponse>
}