package com.example.quala.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quala.httpbody.AlcoholInfo
import com.example.quala.httpbody.AlcoholResponse
import com.example.quala.network.QualaAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlcoholViewModel: ViewModel() {

    var allAlcoholOkCode: MutableLiveData<Boolean> = MutableLiveData()

    private var _alcoholList: ArrayList<AlcoholInfo>? = arrayListOf()
    val alcoholList: ArrayList<AlcoholInfo>?
        get() = _alcoholList

    fun requestAllAlcohol() {
        QualaAPI.requestAllAlcohol().enqueue(object : Callback<AlcoholResponse> {
            override fun onResponse(call: Call<AlcoholResponse>, response: Response<AlcoholResponse>) {

                _alcoholList = response.body()?.alcohols

                if (response.isSuccessful) {
                    allAlcoholOkCode.postValue(true)
                    Log.d("[Quala API] alcohol", "모든 술 조회 요청 성공")
                } else {
                    Log.d("[Quala API] alcohol", "모든 술 조회 요청 실패, error : ${response.code()}, ${response.errorBody()?.string()!!}")
                }
            }

            override fun onFailure(call: Call<AlcoholResponse>, t: Throwable) {
                allAlcoholOkCode.postValue(false)
                Log.d("[Quala API] alcohol", "모든 술 조회 요청 실패, Throwable : ${t.message}")
            }

        })
    }
}