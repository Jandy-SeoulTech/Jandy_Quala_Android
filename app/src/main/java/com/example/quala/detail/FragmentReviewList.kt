package com.example.quala.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quala.RecyclerDecorationGap
import com.example.quala.databinding.FragmentReviewListBinding
import com.example.quala.viewmodel.ReviewViewModel

class FragmentReviewList : Fragment() {
    lateinit var binding: FragmentReviewListBinding
    lateinit var adapter: ReviewAdapter
    lateinit var detailActivity: AlcoholDetailActivity
    lateinit var reviewViewModel: ReviewViewModel

    val datas = mutableListOf<Review>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        detailActivity = context as AlcoholDetailActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentReviewListBinding.inflate(inflater, container, false)

        var id = (activity as AlcoholDetailActivity).id

        reviewViewModel = ViewModelProvider(detailActivity).get(ReviewViewModel::class.java)

        callInquireReviewAPI(id)
        subscribeViewModel()

        val layoutManager = LinearLayoutManager(detailActivity)
        binding.recyclerReview.layoutManager = layoutManager
        adapter = ReviewAdapter(datas)
        binding.recyclerReview.adapter = adapter
        binding.recyclerReview.addItemDecoration(RecyclerDecorationGap(30))

        return binding.root
    }

    private fun callInquireReviewAPI(alcoholId: Long) = reviewViewModel.requestInquireReview(alcoholId)

    private fun subscribeViewModel() {
        reviewViewModel.inquireReviewOkCode.observe(detailActivity) {
            if (it) {
                val reviewData = reviewViewModel.inquireReviewList

                if (reviewData.isNullOrEmpty()) {
                    binding.recyclerReview.visibility = View.GONE
                    binding.tvNull.visibility = View.VISIBLE
                } else {
                    binding.recyclerReview.visibility = View.VISIBLE
                    binding.tvNull.visibility = View.GONE

                    reviewData.forEach { i ->
                        datas.add(Review(i.profileImg, i.starPoint, i.writerNickname, i.date, i.content))
                        adapter.notifyDataSetChanged()
                    }
                }
            } else {
                Toast.makeText(detailActivity, "???????????????. ?????? ?????? ????????? ???????????? ????????? ?????? ??????????????????.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}