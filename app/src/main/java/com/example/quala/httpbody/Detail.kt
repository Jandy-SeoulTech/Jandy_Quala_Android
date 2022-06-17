package com.example.quala.httpbody

import com.google.gson.annotations.SerializedName

data class AlcoholDetailResponse (
    @SerializedName("data")
    val alcoholDetail: AlcoholDetail
)

data class AlcoholDetail (
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

data class AlcoholDetailTop (
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
    val category: String = ""
)

data class AlcoholDetailBottom (
    val introduce: String = "",
    val raw: String = "",
    val situation: String = "",
    val food: String = ""
)