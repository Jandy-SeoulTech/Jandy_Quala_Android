package com.example.quala.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quala.httpbody.LoginRequest
import com.example.quala.httpbody.LoginResponse
import com.example.quala.network.QualaAPI
import com.example.quala.sharedpreference.NoToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {

    val loginOkCode: MutableLiveData<Boolean> = MutableLiveData()
    var accessToken: String = ""

    fun requestLogin(loginInfo: LoginRequest) {
        QualaAPI.requstLogin(loginInfo).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                accessToken = "Bearer ${response.body()?.loginData?.accessToken ?: NoToken.NO_TOKEN.name}"

                if (response.isSuccessful) {
                    loginOkCode.postValue(true)
                    Log.d("[Quala API] login", "로그인 요청 성공")
                } else {
                    Log.d("[Quala API] login", "로그인 요청 실패, status code : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginOkCode.postValue(false)
                Log.d("[Quala API] login", "로그인 요청 실패, Throwable : ${t.message}")
            }

        })
    }
}