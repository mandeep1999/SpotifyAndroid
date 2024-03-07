package com.example.mainactivity.login_module.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.example.mainactivity.utils.general_utils.Utility

open class JavaScriptInterface(
    private val context: Context,
    private val activity: Activity,
    private val webView: WebView,
    private val nextScreenCallback: () -> Unit
) {

    @JavascriptInterface
    fun saveTokenOnClient(token: String?) {
        if (token != null && Utility.isValidText(token)) {
            Log.d("access_token", token )
            ObjectGraph.createOauth(accessToken = token)
            nextScreenCallback.invoke()
        }
    }
}