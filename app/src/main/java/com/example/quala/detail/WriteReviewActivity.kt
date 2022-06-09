package com.example.quala.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.example.quala.R
import com.example.quala.databinding.ActivityWriteReviewBinding

class WriteReviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWriteReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_temp)

        binding.apply {
            btnComplete.isEnabled = false

            etReview.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    // edittext에 내용 입력되면 버튼 활성화
                    val review = binding.etReview.text.toString()

                    // 20자 이상시 버튼 활성화
                    binding.btnComplete.isEnabled = review.length >= 20
                }

                override fun afterTextChanged(s: Editable) {
                }
            })

            btnComplete.setOnClickListener {
                val intent = Intent(this@WriteReviewActivity, AlcoholDetailActivity::class.java)
                this@WriteReviewActivity.startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_review, menu)
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