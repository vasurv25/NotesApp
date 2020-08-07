package com.example.notesapp.network

import com.example.utils.*
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.*

interface ApiInf {
    @POST("student/login/")
    fun studentLogin(@Body loginRequestBody: LoginRequest): Observable<Response<LoginResponse>>

    @POST("student/signup/")
    fun studentSignUp(@Body signUpRequestBody: SignUpRequest,
                      @Query(SMS_OTP_FLAG) smsOtpFlag : Boolean,
                      @Query(MAIL_OTP_FLAG) mailOtpFlag : Boolean): Observable<Response<SignUpResponse>>

    @GET("student/forgotPasswordOTP/")
    fun forgetPassword(@Query(FORGET_PASS_USERNAME) username : String,
                       @Query(FORGET_PASS_CHANNEL_ID) channelId : Int): Observable<Response<ForgetPasswordResponse>>

    @PUT("student/updateMobileNumber/")
    fun updateSignUpMobileNumber(@Query(STUDENT_ID) studentId: Int,
                                 @Query(NEW_MOBILE_NUMBER) newMobileNumber: String): Observable<Response<UpdateMobileNumberResponse>>

}