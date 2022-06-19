package com.example.quala.sharedpreference

import android.content.Context

class QualaPrefs(context: Context) {
    private val prefName="quala_prefs"
    private val prefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

    var accessToken:String?
        get() = prefs.getString("accessToken", NoToken.NO_TOKEN.name) ?: NoToken.NO_TOKEN.name
        set(value){
            prefs.edit().putString("accessToken",value).apply()
        }

    var nickname:String?
        get() = prefs.getString("nickname", NoNickname.NO_NICKNAME.name) ?: NoNickname.NO_NICKNAME.name
        set(value){
            prefs.edit().putString("nickname",value).apply()
        }
}