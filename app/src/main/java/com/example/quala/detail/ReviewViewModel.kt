package com.example.quala.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quala.httpbody.AlcoholReview
import com.example.quala.httpbody.InquireReviewResponse
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

    // 리뷰 조회
    val inquireReviewOkCode: MutableLiveData<Boolean> = MutableLiveData()

    private var _inquireReviewList: ArrayList<AlcoholReview>? = arrayListOf()
    val inquireReviewList: ArrayList<AlcoholReview>?
        get() = _inquireReviewList

    fun requstInquireReview(alcoholId: Long) {
        QualaAPI.requstInquireReview(alcoholId).enqueue(object : Callback<InquireReviewResponse> {
            override fun onResponse(call: Call<InquireReviewResponse>, response: Response<InquireReviewResponse>) {

                _inquireReviewList = response.body()?.reviewList

                if (response.isSuccessful) {
                    inquireReviewOkCode.postValue(true)
                    Log.d("[Quala API] review", "리뷰 조회 요청 성공, $inquireReviewList")
                } else {
                    Log.d("[Quala API] review", "리뷰 조회 요청 실패, error : ${response.code()}, ${response.errorBody()?.string()!!}")
                }
            }

            override fun onFailure(call: Call<InquireReviewResponse>, t: Throwable) {
                inquireReviewOkCode.postValue(false)
                Log.d("[Quala API] review", "리뷰 조회 요청 실패, Throwable : ${t.message}")
            }

        })
    }
}