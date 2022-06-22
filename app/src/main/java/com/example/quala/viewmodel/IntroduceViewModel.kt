package com.example.quala.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quala.httpbody.AlcoholConditionalResponse
import com.example.quala.network.QualaAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IntroduceViewModel: ViewModel() {
    val _cAlcoholList = MutableLiveData<AlcoholConditionalResponse>()
    val cAlcoholList: MutableLiveData<AlcoholConditionalResponse>
        get() = _cAlcoholList

    fun requestConditionalAlcohol(levelStats: List<Int>?, situations: List<String>?, category: String?) {
        QualaAPI.requestConditionalAlcohol(levelStats, situations, category).enqueue(object :
            Callback<AlcoholConditionalResponse> {
            override fun onResponse(call: Call<AlcoholConditionalResponse>, response: Response<AlcoholConditionalResponse>) {
                if (response.isSuccessful) {
                    _cAlcoholList.postValue(response.body())
                    Log.d("[Quala API] alcohol", "술 조건 조회 요청 성공")
                } else {
                    Log.d("[Quala API] alcohol", "술 조건 조회 요청 실패, error : ${response.code()}, ${response.errorBody()?.string()!!}")
                }
            }

            override fun onFailure(call: Call<AlcoholConditionalResponse>, t: Throwable) {
                Log.d("[Quala API] alcohol", "술 조건 조회 요청 실패, Throwable : ${t.message}")
            }

        })
    }
}