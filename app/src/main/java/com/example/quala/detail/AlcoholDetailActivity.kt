package com.example.quala.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.quala.R
import com.example.quala.databinding.ActivityAlcoholDetailBinding
import com.example.quala.introduce.IntroduceViewPagerFragmentAdapter
import com.google.android.material.tabs.TabLayoutMediator

class AlcoholDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityAlcoholDetailBinding
    lateinit var viewPagerFragmentAdapter: DetailViewPagerFragmentAdapter
    lateinit var tabTitle: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_temp)

        setViewPagerAndTabLayout()

        binding.apply {
            ivLike.setOnClickListener {
                ivLike.toggle()
            }
            btnReview.setOnClickListener {
                val intent = Intent(this@AlcoholDetailActivity, WriteReviewActivity::class.java)
                this@AlcoholDetailActivity.startActivity(intent)
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