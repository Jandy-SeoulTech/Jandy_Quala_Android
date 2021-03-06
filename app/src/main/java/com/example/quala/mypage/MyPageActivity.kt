package com.example.quala.mypage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.quala.R
import com.example.quala.databinding.ActivityMyPageBinding
import com.example.quala.home.MainActivity
import com.example.quala.introduce.IntroduceActivity
import com.example.quala.recommend.RecommendActivity
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.navigation.NavigationView


class MyPageActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mypageLayout.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        binding.navigationView.setNavigationItemSelectedListener(this)

        binding.mypageLayout.apply {
            ivChangeProfile.setOnClickListener {
                val intent = Intent(this@MyPageActivity, MyInfoChangeActivity::class.java)
                this@MyPageActivity.startActivity(intent)
            }
            groupReview.setOnClickListener {
                val intent = Intent(this@MyPageActivity, MyReviewActivity::class.java)
                this@MyPageActivity.startActivity(intent)
            }
            groupDdibs.setOnClickListener {
                //TODO: ??? ?????? ???????????? ??????
            }
            cvNotice.setOnClickListener {
                //TODO: ???????????? ???????????? ??????
            }
            cvQuestion.setOnClickListener {
                //TODO: 1:1 ?????? ???????????? ??????
            }
            cvPolicy.setOnClickListener {
                //TODO: ?????? ??? ?????? ???????????? ??????
            }
        }

        setRadarGraph()
    }

    private fun setRadarGraph() {
        // ????????? ????????? ?????? ?????????
        val full: ArrayList<RadarEntry> = ArrayList()
        full.add(RadarEntry(3F))
        full.add(RadarEntry(3F))
        full.add(RadarEntry(3F))
        full.add(RadarEntry(3F))
        full.add(RadarEntry(3F))

        // ?????? ?????? ????????? ?????????
        val scores: ArrayList<RadarEntry> = ArrayList()
        scores.add(RadarEntry(3F))
        scores.add(RadarEntry(2F))
        scores.add(RadarEntry(3F))
        scores.add(RadarEntry(1F))
        scores.add(RadarEntry(2F))

        val radarData = RadarData()
        val radarFullSet = RadarDataSet(full, "full")
        val radarDataSet = RadarDataSet(scores, "scores")
        val labels = arrayOf("??????", "??????", "?????????", "?????????", "??????")

        radarFullSet.color = Color.rgb(0, 97, 46)
        radarFullSet.fillColor = Color.rgb(0, 97, 46)
        radarFullSet.setDrawFilled(true)
        radarFullSet.fillAlpha = 360
        radarFullSet.valueFormatter = MyFormatter()

        radarDataSet.color = Color.rgb(0, 97, 46)
        radarDataSet.fillColor = Color.rgb(0, 97, 46)
        radarDataSet.setDrawFilled(true)
        radarDataSet.fillAlpha = 420
        radarDataSet.valueFormatter = MyFormatter()

        radarData.addDataSet(radarFullSet)
        radarData.addDataSet(radarDataSet)

        binding.mypageLayout.radarChart.apply {
            // ?????? ?????? ??? ?????? ??????
            webColor = Color.rgb(0, 97, 46)
            webColorInner = Color.rgb(0, 97, 46)
            webLineWidth = 0.5f
            webLineWidthInner = 0.5f

            // ???????????? ?????? ??????
            data = radarData
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            // ????????? ??? ?????? ??????
            yAxis.axisMinimum = 0f
            yAxis.axisMaximum = 3f
            yAxis.setLabelCount(4, true)

            // ??????, ?????? ??????
            isRotationEnabled = false
            setTouchEnabled(false)

            // ?????? ????????? ???????????? ??????
            legend.isEnabled = false
            description.isEnabled = false
            yAxis.setDrawLabels(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_mypage, menu)
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
            }
        }
        return false
    }

    private var backPressedTime: Long = 0

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawers()
        } else {
            // 2?????? ?????? ???????????? ??? ??????
            if (System.currentTimeMillis() - backPressedTime < 2000) {
                finish()
            }
            Toast.makeText(this, "'??????' ????????? ?????? ??? ???????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
            backPressedTime = System.currentTimeMillis()
        }
    }
}

// ???????????? ??? ???????????? ?????? ???????????? ????????? ?????? ?????? ?????????
class MyFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return ""
    }
}
