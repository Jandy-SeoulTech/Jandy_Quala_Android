package com.example.quala.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object QualaService {

    private const val BASE_URL: String = "https://quala.kro.kr/api/v1/"

    private val intercepter = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .build()
        chain.proceed(newRequest)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(intercepter)
        .build()

    val retrofit: Retrofit
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service: IQualaService = retrofit.create(IQualaService::class.java)

}