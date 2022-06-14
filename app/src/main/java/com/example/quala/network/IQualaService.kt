package com.example.quala.network

import com.example.quala.httpbody.*
import retrofit2.Call
import retrofit2.http.*

interface IQualaService {
    @POST("auth/login")
    fun requstLogin(@Body loginInfo: LoginRequest): Call<LoginResponse>
}