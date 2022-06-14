package com.example.quala.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quala.httpbody.WriteReviewRequest
import com.example.quala.network.QualaAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewViewModel: ViewModel() {

    // 리뷰 작성
    val writeReviewOkCode: MutableLiveData<Boolean> = MutableLiveData()

    fun requstWriteReview(writeReviewInfo: WriteReviewRequest) {
        QualaAPI.requstWriteReview(writeReviewInfo).enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    writeReviewOkCode.postValue(true)
                    Log.d("[Quala API] review", "리뷰 작성 요청 성공")
                } else {
                    Log.d("[Quala API] review", "리뷰 작성 요청 실패, error : ${response.code()}, ${response.errorBody()?.string()!!}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                writeReviewOkCode.postValue(false)
                Log.d("[Quala API] review", "리뷰 작성 요청 실패, Throwable : ${t.message}")
            }

        })
    }
}