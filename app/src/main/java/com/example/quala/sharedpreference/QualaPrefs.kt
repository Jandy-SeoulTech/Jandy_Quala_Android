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

    var refreshToken:String?
        get() = prefs.getString("refreshToken", NoToken.NO_TOKEN.name) ?: NoToken.NO_TOKEN.name
        set(value){
            prefs.edit().putString("refreshToken",value).apply()
        }
}