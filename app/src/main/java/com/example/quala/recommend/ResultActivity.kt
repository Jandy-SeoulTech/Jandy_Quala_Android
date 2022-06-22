package com.example.quala.recommend

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.quala.R
import com.example.quala.databinding.ActivityResultBinding
import com.example.quala.detail.Review
import com.example.quala.detail.ReviewAdapter
import com.example.quala.httpbody.RecommendRequest
import com.example.quala.httpbody.ResultInfo
import com.example.quala.mypage.MyFormatter
import com.example.quala.sharedpreference.QualaApplication
import com.example.quala.viewmodel.RecommendResultViewModel
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding
    lateinit var recommendResultViewModel: RecommendResultViewModel
    lateinit var adapter: ResultAdapter
    val datas = mutableListOf<Result>()

    var sweet = 0
    var acidity = 0
    var plain = 0
    var body = 0
    var percent = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        sweet = intent.getIntExtra("sweet", 0)
        acidity = intent.getIntExtra("acidity", 0)
        plain = intent.getIntExtra("plain", 0)
        body = intent.getIntExtra("body", 0)
        percent = intent.getIntExtra("percent", 0)

        setRadarGraph()

        recommendResultViewModel = ViewModelProvider(this).get(RecommendResultViewModel::class.java)

        val resultData = testResult()
        callRecommendAPI(resultData)
        subscribeViewModel()

        binding.apply {
            btnFinish.setOnClickListener {
                val intent = Intent(this@ResultActivity, RecommendActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }

            adapter = ResultAdapter(datas)
            recyclerRecommend.adapter = adapter
        }
    }

    private fun testResult(): RecommendRequest = RecommendRequest(percent, sweet, acidity, plain, body)

    private fun callRecommendAPI(resultData: RecommendRequest) = recommendResultViewModel.requestRecommendResult(resultData)

    private fun subscribeViewModel() {
        recommendResultViewModel.recommendResultOkCode.observe(this) {
            if (it) {
                val result = recommendResultViewModel.recommendResult

                if (result.isNullOrEmpty()) {
                } else {
                    result.forEach { i ->
                        datas.add(Result(i.id, i.image, i.name))
                        adapter.notifyDataSetChanged()
                    }
                }
            } else {
                Toast.makeText(this, "죄송합니다. 술 추천 결과 조회 요청에 실패하여 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setRadarGraph() {
        // 그래프 배경을 위한 데이터
        val full: ArrayList<RadarEntry> = ArrayList()
        full.add(RadarEntry(3F))
        full.add(RadarEntry(3F))
        full.add(RadarEntry(3F))
        full.add(RadarEntry(3F))
        full.add(RadarEntry(3F))

        // 실제 값이 들어갈 데이터
        val scores: ArrayList<RadarEntry> = ArrayList()
        scores.add(RadarEntry(percent.toFloat()))
        scores.add(RadarEntry(sweet.toFloat()))
        scores.add(RadarEntry(body.toFloat()))
        scores.add(RadarEntry(plain.toFloat()))
        scores.add(RadarEntry(acidity.toFloat()))

        val radarData = RadarData()
        val radarFullSet = RadarDataSet(full, "full")
        val radarDataSet = RadarDataSet(scores, "scores")
        val labels = arrayOf("도수", "당도", "바디감", "고소함", "산도")

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

        binding.radarChart.apply {
            // 차트 안쪽 선 색상 설정
            webColor = Color.rgb(0, 97, 46)
            webColorInner = Color.rgb(0, 97, 46)
            webLineWidth = 0.5f
            webLineWidthInner = 0.5f

            // 데이터와 라벨 설정
            data = radarData
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            // 그래프 값 범위 설정
            yAxis.axisMinimum = 0f
            yAxis.axisMaximum = 3f
            yAxis.setLabelCount(4, true)

            // 회전, 터치 방지
            isRotationEnabled = false
            setTouchEnabled(false)

            // 필요 없는거 안보이게 설정
            legend.isEnabled = false
            description.isEnabled = false
            yAxis.setDrawLabels(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_test, menu)
        return true
    }
}