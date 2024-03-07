package com.example.mainactivity

import android.app.Application
import android.content.SharedPreferences
import com.example.mainactivity.app_variable.PreferenceKV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StartApplication : Application() {

    private var preferences: SharedPreferences? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {

        private var instance: StartApplication? = null

        @Synchronized
        fun getInstance(): StartApplication? {
            if (instance == null) {
                instance = StartApplication()
            }
            return instance
        }
    }

    fun getSharedPreferences(): SharedPreferences? {
        if (preferences == null) {
            preferences =
                getInstance()?.getSharedPreferences(PreferenceKV.SHARED_PREFERENCES, MODE_PRIVATE)
        }

        return preferences
    }
}