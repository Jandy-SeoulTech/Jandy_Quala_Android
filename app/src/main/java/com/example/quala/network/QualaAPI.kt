package com.example.quala.network

import com.example.quala.httpbody.*
import retrofit2.Call
import retrofit2.http.*

object QualaAPI {
    fun requstLogin(@Body loginInfo: LoginRequest): Call<LoginResponse> {
        return QualaService.service.requstLogin(loginInfo)
    }

    fun requstWriteReview(@Body writeReviewInfo: WriteReviewRequest): Call<Any> {
        return QualaService.service.requstWriteReview(writeReviewInfo)
    }

    fun requstInquireReview(@Query("alcoholId") alcoholId: Long): Call<InquireReviewResponse> {
        return QualaService.service.requstInquireReview(alcoholId)
    }
}