package com.example.quala.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.quala.databinding.ActivityIntroBinding
import com.example.quala.home.MainActivity
import com.example.quala.sharedpreference.QualaApplication
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class IntroActivity : AppCompatActivity() {

    lateinit var binding: ActivityIntroBinding
    var userId: Long? = 0

    private val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        // 에러가 난 경우
        if (error != null) {
            Log.d("[Quala] login","로그인 실패- $error")
        }
        // 토근이 발급된 경우
        else if (token != null) {
            // userId : 이후 서버 통신에 필요
            UserApiClient.instance.me { user, error ->
                userId = user?.id
            }

            Log.d("[Quala] login", "로그인 성공, userId -> $userId")

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@IntroActivity)) {
                UserApiClient.instance.loginWithKakaoTalk(this@IntroActivity, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this@IntroActivity, callback = callback)
            }
        }
    }
}