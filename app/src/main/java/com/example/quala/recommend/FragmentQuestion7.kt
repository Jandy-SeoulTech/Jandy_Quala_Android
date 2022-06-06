package com.example.quala.recommend

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quala.databinding.FragmentQuestion7Binding

class FragmentQuestion7 : Fragment() {

    lateinit var binding: FragmentQuestion7Binding
    lateinit var testActivity: TestActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        testActivity = context as TestActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentQuestion7Binding.inflate(inflater, container, false)

        binding.btnSelect1.setOnClickListener {
            (activity as TestActivity).lastQuestion("low")
        }

        binding.btnSelect2.setOnClickListener {
            (activity as TestActivity).lastQuestion("middle")
        }

        binding.btnSelect3.setOnClickListener {
            (activity as TestActivity).lastQuestion("high")
        }

        return binding.root
    }
}