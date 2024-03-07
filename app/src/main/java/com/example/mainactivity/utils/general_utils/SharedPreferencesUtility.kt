package com.example.mainactivity.utils.general_utils

import android.content.SharedPreferences
import com.example.mainactivity.StartApplication

object SharedPreferencesUtility {

    fun getSharedPreferences(): SharedPreferences? {
        return StartApplication.getInstance()?.getSharedPreferences();
    }
}