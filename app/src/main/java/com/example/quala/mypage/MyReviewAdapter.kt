package com.example.quala.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quala.databinding.RecyclerMyReviewBinding

class MyReviewViewHolder(val binding: RecyclerMyReviewBinding): RecyclerView.ViewHolder(binding.root)

class MyReviewAdapter(val reviewData: List<MyReview>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return reviewData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyReviewViewHolder(RecyclerMyReviewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyReviewViewHolder).binding
        binding.apply {
            tvName.text = reviewData[position].name
            ratingBar.starProgress = reviewData[position].score
            tvDate.text = reviewData[position].dateBefore
            tvReview.text = reviewData[position].review
        }
    }
}