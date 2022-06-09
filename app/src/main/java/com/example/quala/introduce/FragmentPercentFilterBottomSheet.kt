package com.example.quala.introduce

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.quala.R
import com.example.quala.databinding.FragmentPercentFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentPercentFilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var introduceActivity: IntroduceActivity
    var percentList: ArrayList<String> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        introduceActivity = context as IntroduceActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentPercentFilterBottomSheetBinding.inflate(inflater, container, false)

        binding.apply {
            btnApply.setOnClickListener {
                if (cb0To5.isChecked)
                    percentList.add("0도 ~ 5도")
                if (cb5To10.isChecked)
                    percentList.add("5도 ~ 10도")
                if (cb10To.isChecked)
                    percentList.add("10도 ~")

                (activity as IntroduceActivity).filterPercent(percentList)
                dismiss()
            }
        }

        return binding.root
    }

    // 모달 스타일 적용
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    companion object {
        const val TAG = "FragmentPercentFilterBottomSheet"
        fun newInstance(): FragmentPercentFilterBottomSheet{
            return FragmentPercentFilterBottomSheet()
        }
    }
}