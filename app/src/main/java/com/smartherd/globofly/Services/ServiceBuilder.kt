package com.smartherd.globofly.Services

import android.os.Build
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    //value chjange me myself
    private const val URL="https://gaurav-server.herokuapp.com/"

    //Create logger
    private val logger=HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //Create a Custom Interceptor to apply Headers application wide
    val hedaerInterceptor:Interceptor = object : Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            var request:Request = chain.request()

            request = request.newBuilder()
                .addHeader("x-device-type",Build.DEVICE)
                .addHeader("Accept_Language",Locale.getDefault().language)
                .build()

            val response:Response = chain.proceed(request)
            return response
        }
    }

    //CREATE OKHTTP CLIENT
    private val okHttp:OkHttpClient.Builder = OkHttpClient.Builder()
                        .connectTimeout(15,TimeUnit.SECONDS)
                        .addInterceptor(hedaerInterceptor)
                        .addInterceptor(logger)

    //CREATE RETROFIT BUILDER
    private val builder:Retrofit.Builder=Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create()).client(okHttp.build())

    //CREATE RETROFIT INSTANCE
    private val retrofit:Retrofit= builder.build()

    fun<T> buildService(serviceType:Class<T>):T{
        Log.e("service building","in progress")
        return  retrofit.create(serviceType)
    }

}