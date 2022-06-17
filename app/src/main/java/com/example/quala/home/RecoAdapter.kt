package com.example.quala.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quala.databinding.RecyclerRecoItemBinding
import com.example.quala.detail.AlcoholDetailActivity

class RecoViewHolder(val binding: RecyclerRecoItemBinding): RecyclerView.ViewHolder(binding.root)

class RecoAdapter(val recoData: List<RecoData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context

    // 항목의 개수 반환
    override fun getItemCount(): Int {
        return recoData.size
    }

    // ViewHolder를 준비하기 위해 호출, ViewHolder 객체를 생성해서 리턴
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return RecoViewHolder(RecyclerRecoItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    // onCreateViewHolder에서 리턴된 ViewHolder 객체의 뷰 항목 데이터를 출력하거나 이벤트를 걸기 위해 호출
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as RecoViewHolder).binding
        binding.ivItemImg.setImageResource(recoData[position].img)
        binding.tvItemName.text = recoData[position].title

        //TODO: 디테일 액티비티로 넘어가기, id 넘기기
        holder.itemView.setOnClickListener {
            val intent = Intent(context, AlcoholDetailActivity::class.java)
            context.startActivity(intent)
        }
    }
}