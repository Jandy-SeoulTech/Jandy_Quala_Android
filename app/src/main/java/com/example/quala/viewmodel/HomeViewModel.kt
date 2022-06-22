package com.example.quala.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quala.httpbody.*
import com.example.quala.network.QualaAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

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

    val _cAlcoholList = MutableLiveData<AlcoholConditionalResponse>()
    val cAlcoholList: MutableLiveData<AlcoholConditionalResponse>
        get() = _cAlcoholList

    fun requestConditionalAlcohol(levelStats: List<Int>?, situations: List<String>?, category: String?) {
        QualaAPI.requestConditionalAlcohol(levelStats, situations, category).enqueue(object : Callback<AlcoholConditionalResponse> {
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

    val userRecommendOkCode: MutableLiveData<Boolean> = MutableLiveData()

    private var _userRecommend: ArrayList<ResultInfo>? = arrayListOf()
    val userRecommend: ArrayList<ResultInfo>?
        get() = _userRecommend

    fun requestuserRecommend() {
        QualaAPI.requestUserRecommend().enqueue(object: Callback<RecommendResponse> {
            override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {

                _userRecommend = response.body()?.result

                if (response.isSuccessful) {
                    userRecommendOkCode.postValue(true)
                    Log.d("[Quala API] recommend", "홈 화면 유저 추천 정보 조회 요청 성공")
                } else {
                    Log.d("[Quala API] recommend", "홈 화면 유저 추천 정보 조회 요청 실패, error : ${response.code()}, ${response.errorBody()?.string()!!}")
                }
            }

            override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                userRecommendOkCode.postValue(false)
                Log.d("[Quala API] recommend", "홈 화면 유저 추천 정보 조회 요청 실패, Throwable : ${t.message}")
            }

        })
    }
}