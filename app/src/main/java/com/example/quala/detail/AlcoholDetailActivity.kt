package com.example.quala.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.quala.R
import com.example.quala.databinding.ActivityAlcoholDetailBinding

class AlcoholDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityAlcoholDetailBinding
    private var reviewBottomSheet: FragmentReviewBottomSheet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlcoholDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_temp)

        binding.apply {
            ivLike.setOnClickListener {
                ivLike.toggle()
            }
            btnReview.setOnClickListener {
                val intent = Intent(this@AlcoholDetailActivity, WriteReviewActivity::class.java)
                this@AlcoholDetailActivity.startActivity(intent)
            }
        }

//        reviewBottomSheet = FragmentReviewBottomSheet.show(supportFragmentManager, R.id.view_bottom_sheet)
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
            R.id.alarm -> {
                Toast.makeText(this, "알람 버튼 클릭", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}