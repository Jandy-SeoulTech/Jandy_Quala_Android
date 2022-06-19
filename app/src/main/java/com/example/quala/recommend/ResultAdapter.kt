package com.example.quala.recommend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quala.R
import com.example.quala.databinding.RecyclerResultBinding

class ResultViewHolder(val binding: RecyclerResultBinding): RecyclerView.ViewHolder(binding.root)

class ResultAdapter(val resultData: List<Result>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return resultData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ResultViewHolder(RecyclerResultBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ResultViewHolder).binding
        binding.apply {
            ivImage.setImageResource(R.drawable.item_temp)
            tvName.text = resultData[position].name
        }
    }
}