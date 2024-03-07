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

/**
 * Developed by Mandeep Singh on 07-03-2024.
 * In case of token expiration, we usually have a flow of refresh token to get the new auth token.
 * But here we can't have that. So here, we just logout the user, and take him to the login screen.
 */
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
