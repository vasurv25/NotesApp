package com.example.notesapp.network

import android.content.Context
import com.example.notesapp.BuildConfig
import com.example.utils.HTTPCLIENT_CONNECT_TIMEOUT
import com.example.utils.HTTPCLIENT_READ_TIMEOUT
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 *  This class is a implementation of {@ApiInf}. It uses Retrofit for network connectivity.
 */
class RetrofitApiHandler(private val context: Context){

    fun create(): ApiInf {
        val gsonBuilder = GsonBuilder()
            .setLenient()
            .create()

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(HTTPCLIENT_READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(HTTPCLIENT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(ConnectivityInterceptor(context))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(ApiInf::class.java)
    }
}