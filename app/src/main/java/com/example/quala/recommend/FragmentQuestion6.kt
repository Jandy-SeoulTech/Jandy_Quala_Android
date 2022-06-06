package com.example.quala.recommend

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quala.databinding.FragmentQuestion6Binding

class FragmentQuestion6 : Fragment() {

    lateinit var binding: FragmentQuestion6Binding
    lateinit var testActivity: TestActivity
    val nextFragment = FragmentQuestion7()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        testActivity = context as TestActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentQuestion6Binding.inflate(inflater, container, false)

        binding.btnSelect1.setOnClickListener {
            (activity as TestActivity).moveNext(nextFragment, "plain")
        }
        binding.btnSelect2.setOnClickListener {
            (activity as TestActivity).moveNext(nextFragment, "body")
        }

        return binding.root
    }
}