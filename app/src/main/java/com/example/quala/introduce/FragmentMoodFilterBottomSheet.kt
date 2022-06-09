package com.example.quala.introduce

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.quala.R
import com.example.quala.databinding.FragmentMoodFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentMoodFilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var introduceActivity: IntroduceActivity
    var moodList: ArrayList<String> = arrayListOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        introduceActivity = context as IntroduceActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMoodFilterBottomSheetBinding.inflate(inflater, container, false)

        binding.apply {
            btnApply.setOnClickListener {
                val ids = chipGroup.checkedChipIds
                for (id in ids) {
                    when (id) {
                        chipHomeParty.id -> moodList.add("홈 파티")
                        chipAlone.id -> moodList.add("퇴근 후 혼술")
                        chipDate.id -> moodList.add("데이트")
                        chipTrip.id -> moodList.add("여행지")
                    }
                }

                (activity as IntroduceActivity).filterMood(moodList)
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
        const val TAG = "FragmentMoodFilterBottomSheet"
        fun newInstance(): FragmentMoodFilterBottomSheet{
            return FragmentMoodFilterBottomSheet()
        }
    }
}