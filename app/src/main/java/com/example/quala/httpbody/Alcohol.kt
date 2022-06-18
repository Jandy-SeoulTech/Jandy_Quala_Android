package com.example.quala.httpbody

import com.google.gson.annotations.SerializedName

// 전체 조회에서 필요
data class AlcoholResponse (
    @SerializedName("data")
    val alcohols: ArrayList<AlcoholInfo>
)

// 조건 조회에서 필요
data class AlcoholConditionalResponse (
    @SerializedName("data")
    val alcohols: ArrayList<AlcoholInfo>
)

data class AlcoholConditionalRequest (
    val levels: List<Int>?,
    val situations: List<String>?,
    val category: String = ""
    )

// response에서 공통으로 사용
data class AlcoholInfo (
    val alcohol: Alcohol = Alcohol(),
    val reviewCount: Int = 0
)

data class Alcohol (
    val id: Long = 0,
    val name: String = "",
    val image: String = "",
    val size: Int = 0,
    val level: Float = 0.0f,
    val starPoint: Float = 0.0f,
    val sweet: Int = 0,
    val acidity: Int = 0,
    val plain: Int = 0,
    val body: Int = 0,
    val introduce: String = "",
    val raw: String = "",
    val situation: String = "",
    val category: String = "",
    val food: String = ""
)