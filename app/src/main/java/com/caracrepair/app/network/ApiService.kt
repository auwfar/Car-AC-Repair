package com.caracrepair.app.network

import com.caracrepair.app.models.body.AddAddressBody
import com.caracrepair.app.models.body.AddCarBody
import com.caracrepair.app.models.body.BookingServiceBody
import com.caracrepair.app.models.body.ChangePasswordBody
import com.caracrepair.app.models.body.ChangeProfileBody
import com.caracrepair.app.models.body.ForgotPasswordBody
import com.caracrepair.app.models.body.RescheduleServiceBody
import com.caracrepair.app.models.body.ResendOtpForgotPasswordBody
import com.caracrepair.app.models.body.ResendOtpSignUpBody
import com.caracrepair.app.models.body.ResetPasswordBody
import com.caracrepair.app.models.body.SignInBody
import com.caracrepair.app.models.body.SignUpBody
import com.caracrepair.app.models.body.UpdateAddressBody
import com.caracrepair.app.models.body.UpdateCarBody
import com.caracrepair.app.models.body.VerifyOtpForgotPasswordBody
import com.caracrepair.app.models.body.VerifyOtpSignUpBody
import com.caracrepair.app.models.response.AddressResponse
import com.caracrepair.app.models.response.BookingHistoryResponse
import com.caracrepair.app.models.response.BookingServiceResponse
import com.caracrepair.app.models.response.CarResponse
import com.caracrepair.app.models.response.DataResponse
import com.caracrepair.app.models.response.ForgotPasswordResponse
import com.caracrepair.app.models.response.HomePageResponse
import com.caracrepair.app.models.response.RepairShopDetailResponse
import com.caracrepair.app.models.response.RepairShopResponse
import com.caracrepair.app.models.response.RescheduleServiceResponse
import com.caracrepair.app.models.response.ServiceDetailResponse
import com.caracrepair.app.models.response.ServicePaymentResponse
import com.caracrepair.app.models.response.ServiceTimeResponse
import com.caracrepair.app.models.response.SignInResponse
import com.caracrepair.app.models.response.SignUpResponse
import com.caracrepair.app.models.response.StatusResponse
import com.caracrepair.app.models.response.UploadImageResponse
import com.caracrepair.app.models.response.VerifyOtpForgotPasswordResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @POST("api/auth/login")
    suspend fun signIn(@Body request: SignInBody): DataResponse<SignInResponse>

    @POST("api/auth/register")
    suspend fun signUp(@Body request: SignUpBody): DataResponse<SignUpResponse>

    @POST("api/verify-otp-register")
    suspend fun verifyOtpSignUp(@Body request: VerifyOtpSignUpBody): StatusResponse

    @POST("api/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordBody): DataResponse<ForgotPasswordResponse>

    @POST("api/verify-otp-forgot-password")
    suspend fun verifyOtpForgotPassword(@Body request: VerifyOtpForgotPasswordBody): DataResponse<VerifyOtpForgotPasswordResponse>

    @POST("api/resend-otp-register")
    suspend fun resendOtpSignUp(@Body request: ResendOtpSignUpBody): StatusResponse

    @POST("api/resend-otp-forgot-password")
    suspend fun resendOtpForgotPassword(@Body request: ResendOtpForgotPasswordBody): StatusResponse

    @POST("api/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordBody): StatusResponse

    @GET("api/home")
    suspend fun getHomePage(): DataResponse<HomePageResponse>

    @GET("api/carshops")
    suspend fun getRepairShops(): DataResponse<List<RepairShopResponse>>

    @GET("api/carshops/{repair_shop_id}")
    suspend fun getRepairShopDetail(@Path("repair_shop_id") repairShopId: String): DataResponse<RepairShopDetailResponse>

    @POST("api/orders")
    suspend fun bookingService(@Body request: BookingServiceBody): DataResponse<BookingServiceResponse>

    @GET("api/orders/check-availability")
    suspend fun getServiceTimes(@QueryMap queryMap: Map<String, String>): DataResponse<List<ServiceTimeResponse>>

    @GET("api/order-history")
    suspend fun getBookingHistory(): DataResponse<List<BookingHistoryResponse>>

    @GET("api/order/{order_id}")
    suspend fun getServiceDetail(
        @Path("order_id") serviceId: Int,
        @Query("user_id") userId: String
    ): DataResponse<ServiceDetailResponse>

    @POST("api/reschedule-service")
    suspend fun rescheduleService(@Body request: RescheduleServiceBody): DataResponse<RescheduleServiceResponse>

    @GET("api/order-payment/{order_id}")
    suspend fun getServicePayment(
        @Path("order_id") serviceId: Int,
        @Query("user_id") userId: String
    ): DataResponse<ServicePaymentResponse>

    @POST("api/change-password")
    suspend fun changePassword(@Body request: ChangePasswordBody): StatusResponse

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

    @POST("api/upload-payment-proof-image")
    suspend fun uploadPaymentProofImage(
        @PartMap reqBody: MutableMap<String, RequestBody>,
        @Part file: MultipartBody.Part
    ): StatusResponse

    @PUT("api/users/customer/{user_id}")
    suspend fun changeProfile(@Path("user_id") userId: String, @Body request: ChangeProfileBody): StatusResponse

    @Multipart
    @POST("api/utils/upload/image")
    suspend fun uploadImage(@Part file: MultipartBody.Part): DataResponse<UploadImageResponse>
}