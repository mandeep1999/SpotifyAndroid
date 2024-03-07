package com.example.mainactivity.login_module.ui.activites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mainactivity.R
import com.example.mainactivity.databinding.ActivityLoginBinding
import com.example.mainactivity.login_module.ui.fragments.LoginFragment
import com.example.mainactivity.login_module.ui.fragments.WebViewFragment
import com.example.mainactivity.search_module.ui.activities.SearchActivity
import com.example.mainactivity.utils.general_utils.Utility

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initialise()
    }

    private fun initialise() {
        checkRedirection()
    }


    private fun checkRedirection() {
        if (Utility.checkIsUserAuthorised()) {
            startActivity(Intent(this, SearchActivity::class.java))
        } else {
            showLoginFragment()
        }
    }

    private fun showLoginFragment() {
        val fragment = LoginFragment.newInstance()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commitAllowingStateLoss()
    }

    fun showLoginWeb() {
        val url = "https://spotify-token-generation.netlify.app/"
        val fragment = WebViewFragment.newInstance(url)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, fragment)
        transaction.commitAllowingStateLoss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}