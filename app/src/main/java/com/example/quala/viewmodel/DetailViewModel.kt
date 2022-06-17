package com.example.quala.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quala.httpbody.AlcoholDetailBottom
import com.example.quala.httpbody.AlcoholDetailResponse
import com.example.quala.httpbody.AlcoholDetailTop
import com.example.quala.network.QualaAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {

    val alcoholDetailOkCode: MutableLiveData<Boolean> = MutableLiveData()

    lateinit var alcoholDetailTop: AlcoholDetailTop
    lateinit var alcoholDetailBottom: AlcoholDetailBottom

    fun requestAlcoholDetail(id: Long) {
        QualaAPI.requestAlcoholDetail(id).enqueue(object : Callback<AlcoholDetailResponse> {
            override fun onResponse(call: Call<AlcoholDetailResponse>, response: Response<AlcoholDetailResponse>) {

                alcoholDetailTop = response.body()?.let {
                    AlcoholDetailTop(
                        id = it.alcoholDetail.id,
                        name = it.alcoholDetail.name,
                        image = it.alcoholDetail.image,
                        size = it.alcoholDetail.size,
                        level = it.alcoholDetail.level,
                        starPoint = it.alcoholDetail.starPoint,
                        sweet = it.alcoholDetail.sweet,
                        acidity = it.alcoholDetail.acidity,
                        plain = it.alcoholDetail.plain,
                        body = it.alcoholDetail.body,
                        category = it.alcoholDetail.category
                    )
                } ?: AlcoholDetailTop()

                alcoholDetailBottom = response.body()?.let {
                    AlcoholDetailBottom(
                        introduce = it.alcoholDetail.introduce,
                        raw = it.alcoholDetail.raw,
                        situation = it.alcoholDetail.situation,
                        food = it.alcoholDetail.food
                    )
                } ?: AlcoholDetailBottom()

                if (response.isSuccessful) {
                    alcoholDetailOkCode.postValue(true)
                    Log.d("[Quala API] alcohol detail", "술 상세 정보 조회 요청 성공")
                } else {
                    Log.d("[Quala API] alcohol detail", "술 상세 정보 조회 요청 실패, error : ${response.code()}, ${response.errorBody()?.string()!!}")
                }
            }

            override fun onFailure(call: Call<AlcoholDetailResponse>, t: Throwable) {
                alcoholDetailOkCode.postValue(false)
                Log.d("[Quala API] alcohol detail", "술 상세 정보 조회 요청 실패, Throwable : ${t.message}")
            }

        })
    }
}