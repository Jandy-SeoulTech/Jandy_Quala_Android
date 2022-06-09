package com.example.quala.recommend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.quala.R
import com.example.quala.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    lateinit var binding: ActivityTestBinding
    val manager: FragmentManager = supportFragmentManager
    var sweet = 0
    var acidity = 0
    var plain = 0
    var body = 0
    var percent = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_temp)

        // 액티비티 실행시 question1 실행
        val fQuestion1 = FragmentQuestion1()
        val tran = manager.beginTransaction()
        tran.add(R.id.fragment_questions, fQuestion1)
        tran.commit()
    }

    fun moveNext(nextFragment: Fragment, selected: String) {
        val tran = manager.beginTransaction()
        tran.replace(R.id.fragment_questions, nextFragment)
        tran.commit()

        when(selected) {
            "sweet" -> sweet += 1
            "acidity" -> acidity += 1
            "plain" -> plain += 1
            "body" -> body += 1
        }
    }

    fun lastQuestion(selected: String) {
        when(selected) {
            "low" -> percent = 1
            "middle" -> percent = 2
            "high" -> percent = 3
        }

        val intent = Intent(this, ResultActivity::class.java)

        intent.putExtra("sweet", sweet)
        intent.putExtra("acidity", acidity)
        intent.putExtra("plain", plain)
        intent.putExtra("body", body)
        intent.putExtra("percent", percent)

        this.startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_test, menu)
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