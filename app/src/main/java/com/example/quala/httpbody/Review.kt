package com.example.quala.httpbody

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class InquireReviewRequest(
    val alcoholId: Long = 0L
)

data class InquireReviewResponse(
    @SerializedName("data")
    val reviewList: ArrayList<AlcoholReview>
)

data class AlcoholReview (
    val id: Long = 0L,
    val alcoholId: Long = 0L,
    val startPoint: Float = 0.0f,
    val date: LocalDate = LocalDate.now(),
    val writerNickname: String = "",
    val profileImg: String = "",
    val content: String = ""
)

data class WriteReviewRequest(
    val alcoholId: Long = 0L,
    val startPoint: Float = 0.0f,
    val content: String = ""
)