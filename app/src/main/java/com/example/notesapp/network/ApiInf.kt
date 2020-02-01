package com.example.notesapp.network

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInf {
    @POST("student/login/")
    fun studentLogin(@Body loginRequestBody: LoginRequest): Observable<Response<LoginResponse>>

    @POST("student/signup/")
    fun studentSignUp(@Body signUpRequestBody: SignUpRequest): Observable<Response<SignUpResponse>>
}