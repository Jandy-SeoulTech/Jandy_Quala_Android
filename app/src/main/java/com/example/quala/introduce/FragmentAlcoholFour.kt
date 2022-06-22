package com.example.quala.introduce

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quala.databinding.FragmentAlcoholFourBinding
import com.example.quala.httpbody.AlcoholInfo
import com.example.quala.viewmodel.IntroduceViewModel

class FragmentAlcoholFour : Fragment() {

    lateinit var binding: FragmentAlcoholFourBinding
    lateinit var introduceActivity: IntroduceActivity
    val datas = mutableListOf<Alcohol>()
    lateinit var adapter: AlcoholAdapter

    lateinit var cAlcoholViewModel: IntroduceViewModel
    val cAlcoholList = ArrayList<AlcoholInfo>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        introduceActivity = context as IntroduceActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlcoholFourBinding.inflate(inflater, container, false)

        cAlcoholViewModel = ViewModelProvider(this).get(IntroduceViewModel::class.java)

        subscribeViewModel()
        callConditionalAlcoholAPI(null, null, "OTHER")

        binding.recyclerAlcohol.layoutManager = LinearLayoutManager(introduceActivity)
        adapter = AlcoholAdapter(datas)
        binding.recyclerAlcohol.adapter = adapter

        return binding.root
    }

    private fun subscribeViewModel() {
        cAlcoholViewModel.cAlcoholList.observe(introduceActivity) {
            it.alcohols.forEach { i ->
                cAlcoholList.add(i)
            }
            for (i in cAlcoholList){
                datas.add(Alcohol(i.alcohol.id, i.alcohol.image, i.alcohol.name, i.alcohol.level, i.alcohol.size, i.alcohol.starPoint, i.alcohol.introduce))
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun callConditionalAlcoholAPI(levelStats: List<Int>?, situations: List<String>?, category: String) = cAlcoholViewModel.requestConditionalAlcohol(levelStats, situations, category)
}