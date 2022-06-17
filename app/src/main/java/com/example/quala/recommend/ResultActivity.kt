package com.example.quala.recommend

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.quala.R
import com.example.quala.databinding.ActivityResultBinding
import com.example.quala.httpbody.RecommendRequest
import com.example.quala.httpbody.ResultInfo
import com.example.quala.mypage.MyFormatter
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding
    lateinit var recommendResultViewModel: RecommendResultViewModel
    lateinit var recommendResult: ResultInfo

    var sweet = 0
    var acidity = 0
    var plain = 0
    var body = 0
    var percent = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.btnFinish.setOnClickListener {
            val intent = Intent(this, RecommendActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
    }

    private fun testResult(): RecommendRequest = RecommendRequest(percent, sweet, acidity, plain, body)

    private fun callRecommendAPI(resultData: RecommendRequest) = recommendResultViewModel.requestRecommendResult(resultData)

    private fun subscribeViewModel() {
        recommendResultViewModel.recommendResultOkCode.observe(this) {
            if (it) {
                recommendResult = recommendResultViewModel.recommendResult

                Glide.with(this)
                    .load(recommendResult.image)
                    .error(R.drawable.no_item_temp)
                    .into(binding.imageView)

                binding.apply {
                    tvName.text = recommendResult.name
                    tvPercentNum.text = recommendResult.level.toString()
                    tvVolumeNum.text = recommendResult.size.toString()
                    ratingBar.starProgress = recommendResult.starPoint
                    tvRating.text = recommendResult.starPoint.toString()
                    tvDescription.text = recommendResult.introduce
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
        scores.add(RadarEntry(sweet.toFloat()))
        scores.add(RadarEntry(acidity.toFloat()))
        scores.add(RadarEntry(plain.toFloat()))
        scores.add(RadarEntry(body.toFloat()))
        scores.add(RadarEntry(percent.toFloat()))

        val radarData = RadarData()
        val radarFullSet = RadarDataSet(full, "full")
        val radarDataSet = RadarDataSet(scores, "scores")
        val labels = arrayOf("당도", "산도", "고소함", "바디감", "도수")

        radarFullSet.color = Color.rgb(0, 56, 40)
        radarFullSet.fillColor = Color.rgb(0, 56, 40)
        radarFullSet.setDrawFilled(true)
        radarFullSet.fillAlpha = 230
        radarFullSet.valueFormatter = MyFormatter()

        radarDataSet.color = Color.rgb(158, 164, 170)
        radarDataSet.fillColor = Color.rgb(158, 164, 170)
        radarDataSet.setDrawFilled(true)
        radarDataSet.fillAlpha = 100
        radarDataSet.lineWidth = 1f
        radarDataSet.valueFormatter = MyFormatter()

        radarData.addDataSet(radarFullSet)
        radarData.addDataSet(radarDataSet)

        binding.radarChart.apply {
            // 차트 안쪽 선 색상 설정
            webColor = Color.rgb(100, 100, 100)
            webColorInner = Color.rgb(100, 100, 100)
            webLineWidth = 1f
            webLineWidthInner = 1f

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
}