package com.example.mainactivity.core.utils


import android.content.Intent
import com.example.mainactivity.StartApplication
import com.example.mainactivity.login_module.ui.activites.LoginActivity
import com.example.mainactivity.login_module.utils.ObjectGraph
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor() :
    Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        ObjectGraph.resetOauth()
        takeToLoginScreen()
        return null
    }

    private fun takeToLoginScreen() {
        val intent = Intent(StartApplication.getInstance(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        StartApplication.getInstance()?.startActivity(intent)
    }
}
