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

        binding.layoutReview.setOnClickListener {
            // TODO: 리뷰 많은순 text 바꾸고, 정렬하는 함수 호출
        }

        binding.layoutRating.setOnClickListener {
            // TODO: 별점 높은순 text 바꾸고, 정렬하는 함수 호출
        }

        binding.layoutLike.setOnClickListener {
            // TODO: 좋아요 많은순 text 바꾸고, 정렬하는 함수 호출
        }

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