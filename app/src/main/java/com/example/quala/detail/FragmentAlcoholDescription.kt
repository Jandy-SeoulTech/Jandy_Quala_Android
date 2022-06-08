package com.example.quala.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quala.databinding.FragmentAlcoholDescriptionBinding

class FragmentAlcoholDescription : Fragment() {
    lateinit var binding: FragmentAlcoholDescriptionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlcoholDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }
}