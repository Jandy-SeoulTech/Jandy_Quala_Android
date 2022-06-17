package com.example.quala.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.quala.R
import com.example.quala.databinding.ActivityMainBinding
import com.example.quala.introduce.IntroduceActivity
import com.example.quala.mypage.MyPageActivity
import com.example.quala.recommend.RecommendActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var carouselAdapter: CarouselAdapter
    lateinit var adapter: RecoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainLayout.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        binding.navigationView.setNavigationItemSelectedListener(this)

        // 상단 carousel의 데이터 세팅
        val carouselData = ArrayList<CarouselData>()
        for (i in 1..7){
            carouselData.add(CarouselData(R.drawable.item_temp, "고흥 유자주"))
        }
        carouselAdapter = CarouselAdapter(carouselData)

        // 하단 3칸의 데이터 세팅
        val datas = mutableListOf<RecoData>()
        for (i in 1..10){
            datas.add(RecoData(R.drawable.item_temp, "고흥 유자주"))
        }
        adapter = RecoAdapter(datas)

        binding.apply {
            mainLayout.apply {
                rvCarousel.adapter = carouselAdapter
                rvCarousel.setAlpha(true)
                rvCarousel.setIntervalRatio(0.55f)

                rvRecommend1.adapter = adapter
                rvRecommend2.adapter = adapter
                rvRecommend3.adapter = adapter

                btnTest.setOnClickListener {
                    val intent = Intent(this@MainActivity, RecommendActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_home, menu)
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
            }
            R.id.introduce -> {
                val intent = Intent(this, IntroduceActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
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