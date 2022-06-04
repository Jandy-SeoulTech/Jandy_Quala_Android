package com.example.quala.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quala.databinding.RecyclerCarouselItemBinding
import com.example.quala.detail.AlcoholDetailActivity

class CarouselViewHolder(val binding: RecyclerCarouselItemBinding): RecyclerView.ViewHolder(binding.root)

class CarouselAdapter(var datas: ArrayList<CarouselData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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

        binding.ivImage.setImageResource(datas[position].img)
        binding.tvName.text = datas[position].name

        //TODO: 디테일 액티비티로 넘어가기
        holder.itemView.setOnClickListener {
            val intent = Intent(context, AlcoholDetailActivity::class.java)
            context.startActivity(intent)
        }
    }
}