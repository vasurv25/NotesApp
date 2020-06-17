package com.example.notesapp.network

import com.example.utils.FORGET_PASS_CHANNEL_ID
import com.example.utils.FORGET_PASS_USERNAME
import com.example.utils.MAIL_OTP_FLAG
import com.example.utils.SMS_OTP_FLAG
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
}