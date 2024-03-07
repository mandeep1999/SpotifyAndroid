package com.example.mainactivity.login_module.utils

import android.util.Log
import com.example.mainactivity.app_variable.PreferenceKV
import com.example.mainactivity.login_module.models.Oauth
import com.example.mainactivity.utils.general_utils.SharedPreferencesUtility
import com.example.mainactivity.utils.general_utils.Utility

object ObjectGraph {

    private var oauth: Oauth? = null

    fun getOauth(): Oauth? {
        if (oauth != null) {
            return oauth
        }

        val preferences = SharedPreferencesUtility.getSharedPreferences()
        oauth?.accessToken?.toString()?.let { Log.d("log_19", it) }
        if (preferences != null) {
            val accessToken = preferences.getString(
                PreferenceKV.ACCESS_TOKEN, ""
            )
            val tokenType = preferences.getString(PreferenceKV.ACCESS_TOKEN_TYPE, "Bearer")

            Log.d("log_26", accessToken.toString())

            if (Utility.isValidText(accessToken) && Utility.isValidText(tokenType)) {
                oauth = Oauth(accessToken = accessToken, tokenType = tokenType)

                return oauth
            }
        }

        return oauth
    }

    fun createOauth(accessToken: String) {
        oauth = Oauth(accessToken = accessToken, tokenType = "Bearer")
        saveTokenInSharedPreferences(accessToken)
    }

    private fun saveTokenInSharedPreferences(accessToken: String) {
        val preferences = SharedPreferencesUtility.getSharedPreferences()
        val editor = preferences?.edit()
        editor?.putString(PreferenceKV.ACCESS_TOKEN, accessToken)
        editor?.putString(PreferenceKV.ACCESS_TOKEN_TYPE, "Bearer")
        editor?.apply()
    }

    fun resetOauth() {
        oauth = null
        saveTokenInSharedPreferences("")
    }
}