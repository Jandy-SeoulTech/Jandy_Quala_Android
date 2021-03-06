package com.example.quala.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.quala.databinding.FoodChipBinding
import com.example.quala.databinding.FragmentAlcoholDescriptionBinding
import com.example.quala.httpbody.AlcoholDetailBottom
import com.example.quala.viewmodel.DetailViewModel
import com.google.android.material.chip.Chip

class FragmentAlcoholDescription : Fragment() {
    lateinit var binding: FragmentAlcoholDescriptionBinding
    lateinit var detailActivity: AlcoholDetailActivity
    lateinit var detailViewModel: DetailViewModel
    lateinit var alcoholDetailBottom: AlcoholDetailBottom

    var foods: ArrayList<String> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        detailActivity = context as AlcoholDetailActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlcoholDescriptionBinding.inflate(inflater, container, false)

        var id = (activity as AlcoholDetailActivity).id

        detailViewModel = ViewModelProvider(detailActivity).get(DetailViewModel::class.java)

        callAlcoholDetailAPI(id)

        detailViewModel.alcoholDetailOkCode.observe(detailActivity) {
            if (it) {
                alcoholDetailBottom = detailViewModel.alcoholDetailBottom

                binding.apply {
                    tvDes1.text = alcoholDetailBottom.introduce
                    tvDes2.text = alcoholDetailBottom.raw
                    chipSituation.text = alcoholDetailBottom.situation
                }


                foods = if (alcoholDetailBottom.food.contains(",")) {
                    alcoholDetailBottom.food.split(",") as ArrayList<String>
                } else {
                    arrayListOf(alcoholDetailBottom.food)
                }

                for (food in foods) {
                    val chip = createChip(food)
                    Log.d("Quala - create chip", "$chip")
                    binding.chipGroup2.addView(chip)
                }

            } else {
                Toast.makeText(detailActivity, "???????????????. ??? ?????? ?????? ?????? ????????? ???????????? ????????? ?????? ??????????????????.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun callAlcoholDetailAPI(id: Long) = detailViewModel.requestAlcoholDetail(id)

    private fun createChip(label: String): Chip {
        val chip = FoodChipBinding.inflate(layoutInflater).root
        chip.text = label
        return chip
    }
}