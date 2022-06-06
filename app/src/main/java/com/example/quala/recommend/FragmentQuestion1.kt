package com.example.quala.recommend

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quala.R
import com.example.quala.databinding.FragmentQuestion1Binding

class FragmentQuestion1 : Fragment() {

    lateinit var binding: FragmentQuestion1Binding
    lateinit var testActivity: TestActivity
    val nextFragment = FragmentQuestion2()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        testActivity = context as TestActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentQuestion1Binding.inflate(inflater, container, false)

        binding.btnSelect1.setOnClickListener {
            (activity as TestActivity).moveNext(nextFragment, "acidity")
        }
        binding.btnSelect2.setOnClickListener {
            (activity as TestActivity).moveNext(nextFragment, "body")
        }

        return binding.root
    }
}