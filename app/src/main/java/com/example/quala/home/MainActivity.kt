package com.example.quala.home

import android.annotation.SuppressLint
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
import com.example.quala.httpbody.ResultInfo
import com.example.quala.introduce.IntroduceActivity
import com.example.quala.mypage.MyPageActivity
import com.example.quala.recommend.RecommendActivity
import com.example.quala.sharedpreference.QualaApplication
import com.example.quala.viewmodel.HomeViewModel
import com.google.android.material.navigation.NavigationView
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("NotifyDataSetChanged")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    lateinit var alcoholViewModel: HomeViewModel

    val carouselData = mutableListOf<CarouselData>()
    lateinit var carouselAdapter: CarouselAdapter
    val datas1 = mutableListOf<RecoData>()
    lateinit var adapter1: RecoAdapter
    val datas2 = mutableListOf<RecoData>()
    lateinit var adapter2: RecoAdapter
    val datas3 = mutableListOf<RecoData>()
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

        alcoholViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        subscribeAllAlcoholViewModel()
        subscribeConditionalAlcoholViewModel()
        subscribeUserRecommendViewModel()
        callInquireAllAlcoholAPI()
        callInquireConditionalAlcoholAPI(null, listOf("PARTY"), null)
        callInqureUserRecommendAPI()

        // 요일별 문구 세팅
        setDayText()

        binding.apply {
            mainLayout.apply {
                carouselAdapter = CarouselAdapter(carouselData)
                rvCarousel.adapter = carouselAdapter
                rvCarousel.setAlpha(true)
                rvCarousel.setIntervalRatio(0.55f)

                tvTitle1Main.text = QualaApplication.prefs.nickname
                adapter1 = RecoAdapter(datas1)
                rvRecommend1.adapter = adapter1
                adapter2 = RecoAdapter(datas2)
                rvRecommend2.adapter = adapter2
                adapter3 = RecoAdapter(datas3)
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

    private fun callInquireConditionalAlcoholAPI(levelStats: List<Int>?, situations: List<String>?, category: String?) = alcoholViewModel.requestConditionalAlcohol(levelStats, situations, category)

    private fun callInqureUserRecommendAPI() = alcoholViewModel.requestuserRecommend()

    private fun subscribeAllAlcoholViewModel() {
        alcoholViewModel.allAlcoholOkCode.observe(this) {
            if (it) {
                val alcohols = alcoholViewModel.alcoholList

                if (alcohols.isNullOrEmpty()) {
                    Toast.makeText(this, "조회하는 아이템이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
                else {
                    setDayAlcohol(alcohols)
                    setHotAlcohols(alcohols)
                }
            } else {
                Toast.makeText(this, "죄송합니다. 술 목록 조회 요청에 실패하여 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun subscribeConditionalAlcoholViewModel() {
        alcoholViewModel.cAlcoholList.observe(this) {
            val alcohols = it.alcohols
            setSituationAlcohols(alcohols)
        }
    }

    private fun subscribeUserRecommendViewModel() {
        alcoholViewModel.userRecommendOkCode.observe(this) {
            if (it) {
                val alcohols = alcoholViewModel.userRecommend

                if (alcohols.isNullOrEmpty()) {
                    Toast.makeText(this, "추천 아이템이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    setRecommendAlcohols(alcohols)
                }
            }
        }
    }

    private fun setDayAlcohol(alcohols: ArrayList<AlcoholInfo>) {
        val randomIndexList: MutableSet<Int> = mutableSetOf()
        for (i in 0..8) {
            randomIndexList.add((0 until alcohols.size).random())
        }

        for (i in randomIndexList) {
            carouselData.add(CarouselData(alcohols[i].alcohol.id, alcohols[i].alcohol.image, alcohols[i].alcohol.name))
        }

        carouselAdapter.notifyDataSetChanged()
    }

    private fun setRecommendAlcohols(alcohols: ArrayList<ResultInfo>) {
        for (i in alcohols) {
            datas1.add(RecoData(i.id, i.image, i.name))
        }
        adapter1.notifyDataSetChanged()
    }

    private fun setSituationAlcohols(alcohols: ArrayList<AlcoholInfo>) {
        for (i in alcohols) {
            datas2.add(RecoData(i.alcohol.id, i.alcohol.image, i.alcohol.name))
        }
        adapter2.notifyDataSetChanged()
    }

    private fun setHotAlcohols(alcohols: ArrayList<AlcoholInfo>) {
        val sortedAlcohol = alcohols.sortedByDescending{ it.reviewCount }

        for (i in sortedAlcohol) {
            datas3.add(RecoData(i.alcohol.id, i.alcohol.image, i.alcohol.name))
        }

        adapter3.notifyDataSetChanged()
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