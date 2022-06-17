package com.example.quala.network

import com.example.quala.httpbody.*
import retrofit2.Call
import retrofit2.http.*

interface IQualaService {
    @POST("auth/login")
    fun requestLogin(@Body loginInfo: LoginRequest): Call<LoginResponse>

    @POST("review")
    fun requestWriteReview(@Body writeReviewInfo: WriteReviewRequest): Call<Any>

    @GET("review")
    fun requestInquireReview(@Query("alcoholId") alcoholId: Long): Call<InquireReviewResponse>

    @GET("alcohol/detail")
    fun requestAlcoholDetail(@Query("id") id: Long): Call<AlcoholDetailResponse>

    @POST("recommend")
    fun requestRecommendResult(@Body recommendRequest: RecommendRequest): Call<RecommendResponse>

    @GET("alcohol")
    fun requestAllAlcohol(): Call<AlcoholResponse>
}