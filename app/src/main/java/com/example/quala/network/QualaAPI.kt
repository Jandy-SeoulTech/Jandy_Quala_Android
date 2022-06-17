package com.example.quala.network

import com.example.quala.httpbody.*
import retrofit2.Call
import retrofit2.http.*

object QualaAPI {
    fun requestLogin(@Body loginInfo: LoginRequest): Call<LoginResponse> {
        return QualaService.service.requestLogin(loginInfo)
    }

    fun requestWriteReview(@Body writeReviewInfo: WriteReviewRequest): Call<Any> {
        return QualaService.service.requestWriteReview(writeReviewInfo)
    }

    fun requestInquireReview(@Query("alcoholId") alcoholId: Long): Call<InquireReviewResponse> {
        return QualaService.service.requestInquireReview(alcoholId)
    }

    fun requestAlcoholDetail(@Query("id") id: Long): Call<AlcoholDetailResponse> {
        return QualaService.service.requestAlcoholDetail(id)
    }
}