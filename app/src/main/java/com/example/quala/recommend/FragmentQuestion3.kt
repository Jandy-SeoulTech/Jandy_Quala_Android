package com.example.quala.recommend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import com.example.quala.databinding.FragmentQuestion3Binding

class FragmentQuestion3 : Fragment() {

    lateinit var binding: FragmentQuestion3Binding
    lateinit var testActivity: TestActivity
    val nextFragment = FragmentQuestion4()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        testActivity = context as TestActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentQuestion3Binding.inflate(inflater, container, false)

        binding.btnSelect1.setOnClickListener {
            (activity as TestActivity).moveNext(nextFragment, "acidity")
        }
        binding.btnSelect2.setOnClickListener {
            (activity as TestActivity).moveNext(nextFragment, "plain")
        }

        return binding.root
    }
}