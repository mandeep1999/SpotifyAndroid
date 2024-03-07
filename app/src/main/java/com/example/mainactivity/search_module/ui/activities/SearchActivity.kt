package com.example.mainactivity.search_module.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mainactivity.R
import com.example.mainactivity.databinding.ActivitySearchBinding
import com.example.mainactivity.search_module.ui.fragments.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private var _binding: ActivitySearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
        initialise()
    }

    private fun initialise() {
        setUpToolbar()
        val searchFragment = SearchFragment.newInstance()
        replaceFragment(searchFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commitAllowingStateLoss()
    }

    private fun setUpToolbar() {
        binding.toolbar.title = "Spotify Clone"
        setSupportActionBar(binding.toolbar)
    }
}