package com.example.quala.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.quala.R
import com.example.quala.databinding.ActivityMainBinding
import com.example.quala.httpbody.AlcoholInfo
import com.example.quala.introduce.IntroduceActivity
import com.example.quala.mypage.MyPageActivity
import com.example.quala.recommend.RecommendActivity
import com.example.quala.viewmodel.AlcoholViewModel
import com.google.android.material.navigation.NavigationView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var alcoholViewModel: AlcoholViewModel

    val carouselData = mutableListOf<CarouselData>()
    lateinit var carouselAdapter: CarouselAdapter

    lateinit var adapter1: RecoAdapter
    lateinit var adapter2: RecoAdapter
    lateinit var adapter3: RecoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainLayout.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        binding.navigationView.setNavigationItemSelectedListener(this)

        alcoholViewModel = ViewModelProvider(this).get(AlcoholViewModel::class.java)

        subscribeViewModel()
        callInquireAllAlcoholAPI()

        // 요일별 문구 세팅
        setDayText()

        // 맞춤 술
        val datas1 = mutableListOf<RecoData>()
        for (i in 1..10){
            datas1.add(RecoData(R.drawable.item_temp, "고흥 유자주"))
        }
        adapter1 = RecoAdapter(datas1)

        // 분위기 추천 술
        val datas2 = mutableListOf<RecoData>()
        for (i in 1..10){
            datas2.add(RecoData(R.drawable.item_temp, "고흥 유자주"))
        }
        adapter2 = RecoAdapter(datas2)

        // 인기 술
        val datas3 = mutableListOf<RecoData>()
        for (i in 1..10){
            datas3.add(RecoData(R.drawable.item_temp, "고흥 유자주"))
        }
        adapter3 = RecoAdapter(datas3)

        binding.apply {
            mainLayout.apply {
                carouselAdapter = CarouselAdapter(carouselData)
                rvCarousel.adapter = carouselAdapter
                rvCarousel.setAlpha(true)
                rvCarousel.setIntervalRatio(0.55f)

                rvRecommend1.adapter = adapter1
                rvRecommend2.adapter = adapter2
                rvRecommend3.adapter = adapter3

                btnTest.setOnClickListener {
                    val intent = Intent(this@MainActivity, RecommendActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    startActivity(intent)
                }
            }

        }
    }

    private fun callInquireAllAlcoholAPI() = alcoholViewModel.requestAllAlcohol()

    private fun subscribeViewModel() {
        alcoholViewModel.allAlcoholOkCode.observe(this) {
            if (it) {
                val alcohols = alcoholViewModel.alcoholList

                if (alcohols.isNullOrEmpty()) {
                    Toast.makeText(this, "조회하는 아이템이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    setDayAlcohol(alcohols)
                }
            } else {
                Toast.makeText(this, "죄송합니다. 술 목록 조회 요청에 실패하여 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setDayAlcohol(alcohols: ArrayList<AlcoholInfo>) {
        val randomIndexList: MutableSet<Int> = mutableSetOf()
        for (i in 0..7) {
            randomIndexList.add((0 until alcohols.size).random())
        }

        for (i in randomIndexList) {
            carouselData.add(CarouselData(alcohols[i].alcohol.id, alcohols[i].alcohol.image, alcohols[i].alcohol.name))
        }

        carouselAdapter.notifyDataSetChanged()
    }

    private fun setDayText() {
        val currentDate = Date()
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = currentDate

        val day = calendar.get(Calendar.DAY_OF_WEEK)

        binding.mainLayout.apply {
            when (day) {
                1 -> tvDay.text = "상쾌한 일요일에"
                2 -> tvDay.text = "화이팅 월요일에"
                3 -> tvDay.text = "바쁜 화요일에"
                4 -> tvDay.text = "지치는 수요일에"
                5 -> tvDay.text = "행복한 목요일에"
                6 -> tvDay.text = "불타는 금요일에"
                7 -> tvDay.text = "즐거운 토요일에"
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