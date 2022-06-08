package com.example.quala.recommend

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quala.databinding.ActivityResultBinding
import com.example.quala.mypage.MyFormatter
import com.github.mikephil.charting.data.RadarData
import com.github.mikephil.charting.data.RadarDataSet
import com.github.mikephil.charting.data.RadarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class ResultActivity : AppCompatActivity() {

    lateinit var binding: ActivityResultBinding
    var sweet = 0
    var acidity = 0
    var plain = 0
    var body = 0
    var level = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sweet = intent.getIntExtra("sweet", 0)
        acidity = intent.getIntExtra("acidity", 0)
        plain = intent.getIntExtra("plain", 0)
        body = intent.getIntExtra("body", 0)
        level = intent.getIntExtra("level", 0)

        setRadarGraph()
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
        scores.add(RadarEntry(level.toFloat()))

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