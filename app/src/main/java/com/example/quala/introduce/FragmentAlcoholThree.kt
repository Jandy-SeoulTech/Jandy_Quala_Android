package com.example.quala.introduce

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quala.R
import com.example.quala.databinding.FragmentAlcoholThreeBinding
import com.example.quala.httpbody.AlcoholConditionalRequest
import com.example.quala.httpbody.AlcoholInfo
import com.example.quala.viewmodel.AlcoholViewModel

class FragmentAlcoholThree : Fragment() {

    lateinit var binding: FragmentAlcoholThreeBinding
    lateinit var introduceActivity: IntroduceActivity
    lateinit var adapter: AlcoholAdapter

    lateinit var cAlcoholViewModel: AlcoholViewModel
    val cAlcoholList = ArrayList<AlcoholInfo>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        introduceActivity = context as IntroduceActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlcoholThreeBinding.inflate(inflater, container, false)

        cAlcoholViewModel = ViewModelProvider(this).get(AlcoholViewModel::class.java)

        val condition = AlcoholConditionalRequest(null, null, "TAKJU")
        callConditionalAlcoholAPI(condition)
        subscribeViewModel()

        val datas = mutableListOf<Alcohol>()
        for (i in cAlcoholList){
            datas.add(Alcohol(i.alcohol.image, i.alcohol.name, i.alcohol.level, i.alcohol.size, i.alcohol.starPoint, i.alcohol.introduce))
        }

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
        }
    }

    private fun callConditionalAlcoholAPI(condition: AlcoholConditionalRequest) = cAlcoholViewModel.requestConditionalAlcohol(condition)
}