package com.example.mainactivity.login_module.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.example.mainactivity.utils.general_utils.Utility

/**
 * Bridge class to interact with web view.
 */
open class JavaScriptInterface(
    private val context: Context,
    private val activity: Activity,
    private val webView: WebView,
    private val nextScreenCallback: () -> Unit
) {

    /**
     * Function to be invoked from a web view to save the token on the android app.
     * After successful authentication on the web, we get a token, which is then sent
     * by this function to the app.
     * @param token ->  the access token, to be saved in app.
     */
    @JavascriptInterface
    fun saveTokenOnClient(token: String?) {
        if (token != null && Utility.isValidText(token)) {
            Log.d("access_token", token )
            ObjectGraph.createOauth(accessToken = token)
            nextScreenCallback.invoke()
        }
    }
}