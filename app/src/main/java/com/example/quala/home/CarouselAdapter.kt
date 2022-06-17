package com.example.quala.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quala.R
import com.example.quala.databinding.RecyclerCarouselItemBinding
import com.example.quala.detail.AlcoholDetailActivity

class CarouselViewHolder(val binding: RecyclerCarouselItemBinding): RecyclerView.ViewHolder(binding.root)

class CarouselAdapter(var datas: List<CarouselData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return CarouselViewHolder(RecyclerCarouselItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as CarouselViewHolder).binding

        Glide.with(context)
            .load(datas[position].img)
            .error(R.drawable.no_item_temp)
            .into(binding.ivImage)

        binding.tvName.text = datas[position].name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AlcoholDetailActivity::class.java)
            intent.putExtra("id", datas[position].id)
            context.startActivity(intent)
        }
    }
}