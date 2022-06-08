package com.example.quala.introduce

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class IntroduceViewPagerFragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    // ViewPager에 연결할 fragment 생성
    val fragmentList = listOf<Fragment>(FragmentAlcoholOne(), FragmentAlcoholTwo(), FragmentAlcoholThree(), FragmentAlcoholFour())

    // ViewPager에 연결할 fragment의 갯수 리턴
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    // ViewPager의 각 페이지에서 보여줄 fragment 설정
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}