package com.example.quala.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quala.R
import com.example.quala.databinding.ActivityMyReviewBinding

class MyReviewActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyReviewBinding
    lateinit var adapter: MyReviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_temp)

        val datas = mutableListOf<MyReview>()
        for (i in 1..10){
            datas.add(MyReview("고흥 유자주", 4.0f, "지난달", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."))
        }

        binding.rvReview.layoutManager = LinearLayoutManager(this)
        adapter = MyReviewAdapter(datas)
        binding.rvReview.adapter = adapter
        binding.rvReview.addItemDecoration(RecyclerDecorationGap(15))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_mypage_depth, menu)
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