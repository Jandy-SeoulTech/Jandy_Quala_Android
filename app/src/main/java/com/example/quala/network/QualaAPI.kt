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

    fun requestRecommendResult(@Body recommendRequest: RecommendRequest): Call<RecommendResponse> {
        return QualaService.service.requestRecommendResult(recommendRequest)
    }

    fun requestAllAlcohol(): Call<AlcoholResponse> {
        return QualaService.service.requestAllAlcohol()
    }

    fun requestConditionalAlcohol(levelStats: List<Int>?, situations: List<String>?, category: String): Call<AlcoholConditionalResponse> {
        return QualaService.service.requestConditionalAlcohol(levelStats, situations, category)
    }
}