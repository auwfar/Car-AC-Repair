package com.caracrepair.app.network

import com.caracrepair.app.models.body.BookingServiceBody
import com.caracrepair.app.models.body.ChangePasswordBody
import com.caracrepair.app.models.body.RescheduleServiceBody
import com.caracrepair.app.models.body.ResendOtpForgotPasswordBody
import com.caracrepair.app.models.body.ResendOtpSignUpBody
import com.caracrepair.app.models.body.ResetPasswordBody
import com.caracrepair.app.models.body.ServiceTimesBody
import com.caracrepair.app.models.body.VerifyOtpSignUpBody
import com.caracrepair.app.models.body.SignInBody
import com.caracrepair.app.models.body.SignUpBody
import com.caracrepair.app.models.body.VerifyOtpForgotPasswordBody
import com.caracrepair.app.models.response.AddressResponse
import com.caracrepair.app.models.response.BookingHistoryResponse
import com.caracrepair.app.models.response.BookingServiceResponse
import com.caracrepair.app.models.response.CarResponse
import com.caracrepair.app.models.response.DataResponse
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
import com.caracrepair.app.models.response.VerifyOtpForgotPasswordResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("api/login")
    suspend fun signIn(@Body request: SignInBody): DataResponse<SignInResponse>

    @POST("api/auth/register")
    suspend fun signUp(@Body request: SignUpBody): DataResponse<SignUpResponse>

    @POST("api/verify-otp-register")
    suspend fun verifyOtpSignUp(@Body request: VerifyOtpSignUpBody): StatusResponse

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
    suspend fun getRepairShopDetail(@Path("repair_shop_id") repairShopId: Int): DataResponse<RepairShopDetailResponse>

    @POST("api/order-service")
    suspend fun bookingService(@Body request: BookingServiceBody): DataResponse<BookingServiceResponse>?

    @POST("api/order-times")
    suspend fun getServiceTimes(@Body serviceTimesBody: ServiceTimesBody): DataResponse<List<ServiceTimeResponse>>?

    @GET("api/order-history")
    suspend fun getBookingHistory(): DataResponse<List<BookingHistoryResponse>>?

    @GET("api/order/{order_id}")
    suspend fun getServiceDetail(@Path("order_id") serviceId: Int, @Query("user_id") userId: Int): DataResponse<ServiceDetailResponse>?

    @POST("api/reschedule-service")
    suspend fun rescheduleService(@Body request: RescheduleServiceBody): DataResponse<RescheduleServiceResponse>?

    @GET("api/order-payment/{order_id}")
    suspend fun getServicePayment(@Path("order_id") serviceId: Int, @Query("user_id") userId: Int): DataResponse<ServicePaymentResponse>?

    @POST("api/change-password")
    suspend fun changePassword(@Body request: ChangePasswordBody): StatusResponse?

    @GET("api/cars")
    suspend fun getCars(): DataResponse<List<CarResponse>>

    @GET("api/addresses")
    suspend fun getAddresses(): DataResponse<List<AddressResponse>>
}