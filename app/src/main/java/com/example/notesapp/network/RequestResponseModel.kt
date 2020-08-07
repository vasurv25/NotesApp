package com.example.notesapp.network

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

//Login
data class LoginRequest(
    @SerializedName("username") var username: String?,
    @SerializedName("password") var password: String?,
    @SerializedName("channel") var channel: Int?
) : BaseModel()

data class Data(
    @SerializedName("token") val token: String?,
    @SerializedName("userId") val studentId: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("verified") val verified: Boolean?,
    @SerializedName("mobileNumber") val smsOtp: String?
) : BaseModel()

data class Error(
    @SerializedName("status") val status: String?,
    @SerializedName("statusCode") val statusCode: String?,
    @SerializedName("message") val message: String?
) : BaseModel()

data class LoginResponse(
    @SerializedName("data") val data: Data?,
    @SerializedName("error") val error: Error?
) : BaseModel()

//SignUp
data class SignUpRequest(
    @SerializedName("firstName") var firstName: String?,
    @SerializedName("middleName") var middleName: String?,
    @SerializedName("lastName") var lastName: String?,
    @SerializedName("email") var email: String?,
    @SerializedName("mobileNumber") var mobileNumber: String?,
    @SerializedName("password") var password: String?,
    @SerializedName("channel") var channel: Int?
) : BaseModel()

data class DataSignUp(
    @SerializedName("studentId") val studentId: Int?,
    @SerializedName("firstName") val firstName: String?,
    @SerializedName("middleName") val middleName: String?,
    @SerializedName("lastName") val lastName: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("mobileNumber") val mobileNumber: String?,
    @SerializedName("channel") val channel: String?,
    @SerializedName("smsOtp") val smsOtp: Int?,
    @SerializedName("mailOtp") val mailOtp: String?
) : BaseModel()

data class ErrorSignUp(
    @SerializedName("status") val status: String?,
    @SerializedName("statusCode") val statusCode: String?,
    @SerializedName("message") val message: String?
) : BaseModel()

data class SignUpResponse(
    @SerializedName("data") val data: DataSignUp?,
    @SerializedName("error") val error: ErrorSignUp?
) : BaseModel()

data class ForgetPasswordResponse(
    @SerializedName("otpDetails") val otpDetails: OtpDetails? = null,
    @SerializedName("error") val error: Error?
) : BaseModel()

data class OtpDetails(
    @SerializedName("mailOtp") val mailOtp: String?,
    @SerializedName("smsOtp") val smsOtp: Int? = 0
) : BaseModel()

data class UpdateMobileNumberResponse(
    @SerializedName("updateMobile") val updateMobile: UpdateMobile?,
    @SerializedName("error") val error: Error?
) : BaseModel()

data class UpdateMobile(
    @SerializedName("newMobileNumber") val newMobileNumber: String?,
    @SerializedName("smsOtp") val smsOtp: Int? = 0
) : BaseModel()
