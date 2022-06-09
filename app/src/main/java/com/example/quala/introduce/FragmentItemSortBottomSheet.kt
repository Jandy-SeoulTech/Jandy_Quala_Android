package com.example.quala.introduce

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.quala.R
import com.example.quala.databinding.FragmentItemSortBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentItemSortBottomSheet : BottomSheetDialogFragment() {

    lateinit var introduceActivity: IntroduceActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        introduceActivity = context as IntroduceActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentItemSortBottomSheetBinding.inflate(inflater, container, false)

        binding.layoutRating.setOnClickListener {
            (activity as IntroduceActivity).sortItems("rating")
            dismiss()
        }

        binding.layoutReview.setOnClickListener {
            (activity as IntroduceActivity).sortItems("reviewCnt")
            dismiss()
        }

//        binding.layoutLike.setOnClickListener {
//            (activity as IntroduceActivity).sortItems("likeCnt")
//            dismiss()
//        }

        return binding.root
    }

    // 모달 스타일 적용
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    companion object {
        const val TAG = "FragmentItemSortBottomSheet"
        fun newInstance(): FragmentItemSortBottomSheet{
            return FragmentItemSortBottomSheet()
        }
    }
}