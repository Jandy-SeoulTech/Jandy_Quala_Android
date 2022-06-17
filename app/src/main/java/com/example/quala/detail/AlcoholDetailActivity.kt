package com.example.quala.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.quala.R
import com.example.quala.databinding.ActivityAlcoholDetailBinding
import com.example.quala.httpbody.AlcoholDetailTop
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.roundToInt

class AlcoholDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityAlcoholDetailBinding
    lateinit var viewPagerFragmentAdapter: DetailViewPagerFragmentAdapter
    lateinit var tabTitle: List<String>
    lateinit var detailViewModel: DetailViewModel
    lateinit var alcoholDetailTop: AlcoholDetailTop

    // TODO: introduce 페이지 연동 후 id는 intent로 받아오기
    var id: Long = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_temp)

        setViewPagerAndTabLayout()

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        callAlcoholDetailAPI(id)
        subscribeViewModel()

        binding.apply {
//            ivLike.setOnClickListener {
//                ivLike.toggle()
//            }
            btnReview.setOnClickListener {
                val intent = Intent(this@AlcoholDetailActivity, WriteReviewActivity::class.java)
                this@AlcoholDetailActivity.startActivity(intent)
            }
        }
    }

    private fun callAlcoholDetailAPI(id: Long) = detailViewModel.requestAlcoholDetail(id)

    private fun subscribeViewModel() {
        detailViewModel.alcoholDetailOkCode.observe(this) {
            if (it) {
                alcoholDetailTop = detailViewModel.alcoholDetailTop

                Glide.with(this)
                    .load(alcoholDetailTop.image)
                    .error(R.drawable.no_item_temp)
                    .into(binding.imageView)

                binding.apply {
                    tvName.text = alcoholDetailTop.name
                    tvPercentNum.text = alcoholDetailTop.level.toString()
                    tvVolumeNum.text = alcoholDetailTop.size.toString()
                    ratingBar.starProgress = alcoholDetailTop.starPoint
                    tvRating.text = alcoholDetailTop.starPoint.toString()
                    progressBar1.progress = alcoholDetailTop.sweet
                    progressBar2.progress = alcoholDetailTop.acidity
                    progressBar3.progress = alcoholDetailTop.plain
                    progressBar4.progress = alcoholDetailTop.body
                    when (alcoholDetailTop.level.roundToInt()) {
                        in 0 until 5 -> progressBar5.progress = 1
                        in 5 until 10 -> progressBar5.progress = 2
                        else -> progressBar5.progress = 3
                    }
                }
            } else {
                Toast.makeText(this, "죄송합니다. 술 상세 정보 조회 요청에 실패하여 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setViewPagerAndTabLayout() {
        viewPagerFragmentAdapter = DetailViewPagerFragmentAdapter(this)
        tabTitle = listOf("설명", "리뷰")

        binding.viewPager.adapter = viewPagerFragmentAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId) {
            android.R.id.home -> {
                finish()
                super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}