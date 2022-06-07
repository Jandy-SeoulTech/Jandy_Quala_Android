package com.example.quala.sharedpreference

import android.app.Application
import android.content.Context
import com.example.quala.R
import com.example.quala.login.SocketApplication
import com.kakao.sdk.common.KakaoSdk

class QualaApplication : Application(){
    companion object{
        lateinit var prefs: QualaPrefs

        var appContext : Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = QualaPrefs(applicationContext)

        SocketApplication.appContext = this
        KakaoSdk.init(this,getString(R.string.kakao_app_key))
    }
}