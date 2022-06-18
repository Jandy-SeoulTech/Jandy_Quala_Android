package com.example.quala.introduce

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quala.R
import com.example.quala.databinding.ActivityIntroduceBinding
import com.example.quala.home.MainActivity
import com.example.quala.httpbody.AlcoholInfo
import com.example.quala.mypage.MyPageActivity
import com.example.quala.recommend.RecommendActivity
import com.example.quala.viewmodel.AlcoholViewModel
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class IntroduceActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityIntroduceBinding
    lateinit var viewPagerFragmentAdapter: IntroduceViewPagerFragmentAdapter
    lateinit var tabTitle: List<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroduceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.introduceLayout.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        binding.navigationView.setNavigationItemSelectedListener(this)

        // viewPager와 tablayout 설정
        setViewPagerAndTabLayout()

        binding.introduceLayout.apply {
            //TODO : 서버 연동 후 실제 갯수로 적용
            tvCnt.text = "총 " + "30" + "개"

            linearSortBtn.setOnClickListener {
                FragmentItemSortBottomSheet.newInstance().show(
                    supportFragmentManager, FragmentItemSortBottomSheet.TAG
                )
            }

            chipPercentNotSelected.setOnClickListener {
                FragmentPercentFilterBottomSheet.newInstance().show(
                    supportFragmentManager, FragmentPercentFilterBottomSheet.TAG
                )
            }

            chipPercentSelected.setOnCloseIconClickListener {
                chipPercentNotSelected.visibility = View.VISIBLE
                chipPercentSelected.visibility = View.GONE
                // TODO : filter 해제
            }

            chipMoodNotSelected.setOnClickListener {
                FragmentMoodFilterBottomSheet.newInstance().show(
                    supportFragmentManager, FragmentMoodFilterBottomSheet.TAG
                )
            }

            chipMoodSelected.setOnCloseIconClickListener {
                chipMoodNotSelected.visibility = View.VISIBLE
                chipMoodSelected.visibility = View.GONE
                // TODO : filter 해제
            }
        }
    }

    private fun setViewPagerAndTabLayout() {
        viewPagerFragmentAdapter = IntroduceViewPagerFragmentAdapter(this)
        tabTitle = listOf("탁주", "과일주", "증류주", "기타")

        binding.introduceLayout.apply {
            viewPager.adapter = viewPagerFragmentAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitle[position]
            }.attach()
        }
    }

    fun sortItems(standard: String) {
        binding.introduceLayout.apply {
            when(standard) {
                "reviewCnt" -> {
                    tvSort.text = "리뷰 많은순"
                    // TODO : 정렬 함수
                }
                "rating" -> {
                    tvSort.text = "별점 높은순"
                    // TODO : 정렬 함수
                }
//                "likeCnt" -> {
//                    tvSort.text = "좋아요 많은순"
//                }
            }
        }
    }

    fun filterPercent(percentList: ArrayList<String>) {
        setPercentChip(percentList)
        // TODO: 서버 통신 하면서 필터 적용
    }

    private fun setPercentChip(percentList: ArrayList<String>) {
        var size = percentList.size

        var chipText = if (size == 1) {
            percentList[0]
        } else {
            "${percentList[0]} 외 ${size - 1}"
        }

        binding.introduceLayout.apply {
            chipPercentNotSelected.visibility = View.GONE
            chipPercentSelected.visibility = View.VISIBLE
            chipPercentSelected.text = chipText
        }
    }

    fun filterMood(moodList: ArrayList<String>) {
        setMoodChip(moodList)
        // TODO: 서버 통신 하면서 필터 적용
    }

    private fun setMoodChip(moodList: ArrayList<String>) {
        var size = moodList.size

        var chipText = if (size == 1) {
            moodList[0]
        } else {
            "${moodList[0]} 외 ${size - 1}"
        }

        binding.introduceLayout.apply {
            chipMoodNotSelected.visibility = View.GONE
            chipMoodSelected.visibility = View.VISIBLE
            chipMoodSelected.text = chipText
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_introduce, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }
            R.id.introduce -> {
            }
            R.id.recommend -> {
                val intent = Intent(this, RecommendActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }
            R.id.mypage -> {
                val intent = Intent(this, MyPageActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }
        }
        return false
    }

    private var backPressedTime: Long = 0

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers()
        } else {
            // 2초내 다시 클릭하면 앱 종료
            if (System.currentTimeMillis() - backPressedTime < 2000) {
                finish()
            }
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        }
    }
}