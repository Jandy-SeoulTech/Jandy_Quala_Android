package com.example.quala.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.quala.R
import com.example.quala.databinding.ActivityWriteReviewBinding
import com.example.quala.httpbody.WriteReviewRequest

class WriteReviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWriteReviewBinding
    lateinit var writeReviewViewModel: ReviewViewModel
    var alcoholId: Long = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_temp)

        writeReviewViewModel = ViewModelProvider(this).get(ReviewViewModel::class.java)
        subscribeViewModel()

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
                val writeReviewInfo = getWriteReviewInfo()
                callWriteReviewAPI(writeReviewInfo)
                Log.d("Quala", "$writeReviewInfo")
            }
        }
    }

    private fun subscribeViewModel() {
        writeReviewViewModel.writeReviewOkCode.observe(this) {
            if (it) {
                finish()
            } else {
                Toast.makeText(this, "죄송합니다. 리뷰 작성 요청에 실패하여 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWriteReviewInfo(): WriteReviewRequest {
        return WriteReviewRequest(
            alcoholId = alcoholId,
            starPoint = binding.ratingBar.starProgress,
            content = binding.etReview.text.toString()
        )
    }

    private fun callWriteReviewAPI(writeReviewInfo: WriteReviewRequest) = writeReviewViewModel.requestWriteReview(writeReviewInfo)

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