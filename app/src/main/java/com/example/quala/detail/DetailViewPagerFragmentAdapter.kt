package com.example.quala.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailViewPagerFragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    // ViewPager에 연결할 fragment 생성
    val fragmentList = listOf<Fragment>(FragmentAlcoholDescription(), FragmentReviewList())

    // ViewPager에 연결할 fragment의 갯수 리턴
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // ViewPager의 각 페이지에서 보여줄 fragment 설정
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}