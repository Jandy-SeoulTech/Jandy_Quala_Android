package com.example.quala.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quala.databinding.RecyclerReviewBinding


class ReviewViewHolder(val binding: RecyclerReviewBinding): RecyclerView.ViewHolder(binding.root)

class ReviewAdapter(val reviewData: List<Review>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    override fun getItemCount(): Int {
        return reviewData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return ReviewViewHolder(RecyclerReviewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ReviewViewHolder).binding

        binding.apply {
            Glide.with(context)
                .load(reviewData[position].profile)
                .into(binding.ivProfile)

            ratingBar.starProgress = reviewData[position].score
            tvRating.text = reviewData[position].score.toString()
            tvNickname.text = reviewData[position].name
            tvDate.text = reviewData[position].date
            tvContent.text = reviewData[position].content
        }

    }
}