package com.example.quala.detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quala.R
import com.example.quala.RecyclerDecorationGap
import com.example.quala.databinding.FragmentReviewListBinding

class FragmentReviewList : Fragment() {
    lateinit var binding: FragmentReviewListBinding
    lateinit var adapter: ReviewAdapter
    lateinit var detailActivity: AlcoholDetailActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        detailActivity = context as AlcoholDetailActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentReviewListBinding.inflate(inflater, container, false)

        val datas = mutableListOf<Review>()
        for (i in 1..10){
            datas.add(Review(R.drawable.item_temp, 4.0f, "밍나", "2022.06.08", "제 인생은 이 술을 먹기 전과 후로 나뉩니다. 그만큼 너무 맛있어요. 제 인생은 이 술을 먹기 전과 후로 나뉩니다. 그만큼 너무 맛있어요. 제 인생은 이 술을 먹기 전과 후로 나뉩니다. 그만큼 너무 맛있어요. 제 인생은 이 술을 먹기 전과 후로 나뉩니다. 그만큼 너무 맛있어요."))
        }

        binding.recyclerReview.layoutManager = LinearLayoutManager(detailActivity)
        adapter = ReviewAdapter(datas)
        binding.recyclerReview.adapter = adapter
        binding.recyclerReview.addItemDecoration(RecyclerDecorationGap(30))

        return binding.root
    }
}