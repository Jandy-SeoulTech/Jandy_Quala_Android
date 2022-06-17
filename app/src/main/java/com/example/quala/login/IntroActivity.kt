package com.example.quala.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.quala.R
import com.example.quala.databinding.ActivityIntroBinding
import com.example.quala.home.MainActivity
import com.example.quala.httpbody.LoginRequest
import com.example.quala.sharedpreference.QualaApplication
import com.example.quala.viewmodel.LoginViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class IntroActivity : AppCompatActivity() {

    lateinit var binding: ActivityIntroBinding
    lateinit var loginViewModel: LoginViewModel
    var userId: String = ""
    var userNickname: String = ""

    private val callback : (OAuthToken?, Throwable?) -> Unit = { token, error ->
        // 에러가 난 경우
        if (error != null) {
            Log.d("[Quala API] kakao login","카카오 로그인 실패 - $error")
        }
        // 토근이 발급된 경우
        else if (token != null) {
            UserApiClient.instance.me { user, error ->
                if (user != null) {
                    userId = user.id.toString()
                    userNickname = user.kakaoAccount?.profile?.nickname.toString()

                    val loginInfo = getLoginInfo()
                    callLoginAPI(loginInfo)
                } else {
                    Log.d("[Quala API] kakao login", "카카오 로그인 실패 - $error")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        subscribeViewModel()

        binding.btnStart.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@IntroActivity)) {
                UserApiClient.instance.loginWithKakaoTalk(this@IntroActivity, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this@IntroActivity, callback = callback)
            }
        }
    }

    private fun subscribeViewModel() {
        loginViewModel.loginOkCode.observe(this) {
            if(it) {
                QualaApplication.prefs.accessToken = loginViewModel.accessToken

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "죄송합니다. 로그인 요청에 실패하여 잠시후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLoginInfo(): LoginRequest {
        return LoginRequest(
            socialId = userId,
            socialType = getString(R.string.social_type_kakao),
            nickName = userNickname
        )
    }

    private fun callLoginAPI(loginInfo: LoginRequest) = loginViewModel.requestLogin(loginInfo)
}