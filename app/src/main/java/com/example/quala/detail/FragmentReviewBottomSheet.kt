package com.example.quala.detail

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.example.quala.R
import com.example.quala.databinding.LayoutReviewCollapseBinding
import com.example.quala.databinding.LayoutReviewExpandBinding
import kr.co.prnd.persistbottomsheetfragment.PersistBottomSheetFragment

class FragmentReviewBottomSheet : PersistBottomSheetFragment<LayoutReviewCollapseBinding, LayoutReviewExpandBinding>(
    R.layout.layout_review_collapse,
    R.layout.layout_review_expand) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    companion object {
        private val TAG = FragmentReviewBottomSheet::class.simpleName
        fun show(
            fragmentManager: FragmentManager,
            @IdRes containerViewId: Int,
        ): FragmentReviewBottomSheet =
            fragmentManager.findFragmentByTag(TAG) as? FragmentReviewBottomSheet
                ?: FragmentReviewBottomSheet().apply {
                    fragmentManager.beginTransaction()
                        .replace(containerViewId, this, TAG)
                        .commitAllowingStateLoss()
                }
    }
}