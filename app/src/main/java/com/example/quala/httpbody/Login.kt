package com.example.quala.httpbody

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val socialId: String = "",
    val socialType: String = "",
    val nickName: String = ""
)

data class LoginResponse(
    @SerializedName("data")
    val loginData: LoginInfo
)

data class LoginInfo (
    val accessToken: String = ""
)
