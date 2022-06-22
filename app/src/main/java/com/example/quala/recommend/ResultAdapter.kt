package com.example.quala.recommend

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quala.R
import com.example.quala.databinding.RecyclerResultBinding
import com.example.quala.detail.AlcoholDetailActivity

class ResultViewHolder(val binding: RecyclerResultBinding): RecyclerView.ViewHolder(binding.root)

class ResultAdapter(val resultData: List<Result>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context

    override fun getItemCount(): Int {
        return resultData.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return ResultViewHolder(RecyclerResultBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ResultViewHolder).binding
        binding.apply {
            Glide.with(context)
                .load(resultData[position].img)
                .error(R.drawable.no_item_temp)
                .into(binding.ivImage)
            tvName.text = resultData[position].name
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AlcoholDetailActivity::class.java)
            intent.putExtra("id", resultData[position].id)
            context.startActivity(intent)
        }
    }
}