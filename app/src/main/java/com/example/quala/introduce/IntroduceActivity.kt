package com.example.quala.introduce

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.quala.R
import com.example.quala.databinding.ActivityIntroduceBinding
import com.example.quala.home.MainActivity
import com.example.quala.mypage.MyPageActivity
import com.example.quala.recommend.RecommendActivity
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class IntroduceActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityIntroduceBinding
    lateinit var viewPagerFragmentAdapter: ViewPagerFragmentAdapter
    lateinit var tabTitle: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroduceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.introduceLayout.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_temp)

        binding.navigationView.setNavigationItemSelectedListener(this)

        // viewPager와 tablayout 설정
        setViewPagerAndTabLayout()

        binding.introduceLayout.apply {
            //TODO: 서버 연동 후 실제 갯수로 적용
            filterCnt = "총 " + "30" + "개"

            linearSortBtn.setOnClickListener {
                FragmentItemSortBottomSheet.newInstance().show(
                    supportFragmentManager, FragmentItemSortBottomSheet.TAG
                )
            }
        }
    }

    private fun setViewPagerAndTabLayout() {
        viewPagerFragmentAdapter = ViewPagerFragmentAdapter(this)
        tabTitle = listOf("탁주", "과일주", "증류주", "기타")

        binding.introduceLayout.apply {
            viewPager.adapter = viewPagerFragmentAdapter
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabTitle[position]
            }.attach()
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
            R.id.alarm -> {
                Toast.makeText(this, "알람 버튼 클릭", Toast.LENGTH_SHORT).show()
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
            }
            R.id.introduce -> {
            }
            R.id.recommend -> {
                val intent = Intent(this, RecommendActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }
            R.id.mypage -> {
                val intent = Intent(this, MyPageActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }
        }
        return false
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers()
        }else{
            super.onBackPressed()
        }
    }
}