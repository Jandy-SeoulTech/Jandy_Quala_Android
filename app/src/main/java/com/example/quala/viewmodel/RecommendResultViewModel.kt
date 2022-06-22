package com.example.quala.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quala.httpbody.*
import com.example.quala.network.QualaAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendResultViewModel: ViewModel() {

    val recommendResultOkCode: MutableLiveData<Boolean> = MutableLiveData()

    private var _recommendResult: ArrayList<ResultInfo>? = arrayListOf()
    val recommendResult: ArrayList<ResultInfo>?
        get() = _recommendResult

    fun requestRecommendResult(recommendRequest: RecommendRequest) {
        QualaAPI.requestRecommendResult(recommendRequest).enqueue(object: Callback<RecommendResponse> {
            override fun onResponse(call: Call<RecommendResponse>, response: Response<RecommendResponse>) {

                _recommendResult = response.body()?.result

                if (response.isSuccessful) {
                    recommendResultOkCode.postValue(true)
                    Log.d("[Quala API] recommend", "추천 테스트 결과 정보 조회 요청 성공")
                } else {
                    Log.d("[Quala API] recommend", "추천 테스트 결과 정보 조회 요청 실패, error : ${response.code()}, ${response.errorBody()?.string()!!}")
                }
            }

            override fun onFailure(call: Call<RecommendResponse>, t: Throwable) {
                recommendResultOkCode.postValue(false)
                Log.d("[Quala API] recommend", "추천 테스트 결과 정보 조회 요청 실패, Throwable : ${t.message}")
            }

        })
    }
}