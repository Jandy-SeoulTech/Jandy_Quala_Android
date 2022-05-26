package com.example.quala.introduce

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quala.R
import com.example.quala.databinding.FragmentAlcoholTwoBinding

class FragmentAlcoholTwo : Fragment() {

    lateinit var binding: FragmentAlcoholTwoBinding
    lateinit var introduceActivity: IntroduceActivity
    lateinit var adapter: AlcoholAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        introduceActivity = context as IntroduceActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlcoholTwoBinding.inflate(inflater, container, false)

        val datas = mutableListOf<Alcohol>()
        for (i in 1..10){
            datas.add(Alcohol(R.drawable.item_temp, "고흥유자주", 8.0f, 500, 3.8f, "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."))
        }

        binding.recyclerAlcohol.layoutManager = LinearLayoutManager(introduceActivity)
        adapter = AlcoholAdapter(datas)
        binding.recyclerAlcohol.adapter = adapter

        return binding.root
    }
}