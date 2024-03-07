package com.example.mainactivity.search_module.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mainactivity.R
import com.example.mainactivity.app_variable.JsonKeys
import com.example.mainactivity.databinding.ActivityDetailsBinding
import com.example.mainactivity.search_module.data.models.dtos.Icon
import com.example.mainactivity.search_module.ui.uiHelpers.DetailsActivityUIHelper
import com.example.mainactivity.search_module.view_models.DetailsViewModel
import com.example.mainactivity.utils.general_utils.Utility
import com.example.mainactivity.utils.view_utils.ViewUtility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private var _viewModel: DetailsViewModel? = null
    private val viewModel get() = _viewModel

    private var _uiHelper: DetailsActivityUIHelper? = null
    private val uiHelper get() = _uiHelper!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        _binding =
            DataBindingUtil.setContentView<ActivityDetailsBinding>(this, R.layout.activity_details)
        _uiHelper = DetailsActivityUIHelper()

        initialise()
    }

    private fun initialise() {
        setUpToolbar("")
        setUpData()
    }

    private fun setUpData() {
        val id = intent?.getStringExtra(JsonKeys.ID)
        val type = intent?.getStringExtra(JsonKeys.TYPE)
        if (Utility.isValidText(id) && Utility.isValidText(type)) {
            val fragment = uiHelper.getDetailFragment(id = id!!, type = type!!)
            if (fragment != null) {
                replaceFragment(fragment)
            }
        } else {
            finish()
        }
    }

    fun setUpToolbar(toolBarText: String?) {
        binding.toolbar.title = toolBarText
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

   fun setCollapseImage(icon: Icon) {
        ViewUtility.loadIconIntoImageView(
            icon,
            binding.topClImageView,
            binding.topClImageView.context
        )
    }

   fun setCollapseText(text: String?) {
        if (Utility.isValidText(text)) {
            binding.topClTitle.visibility = View.VISIBLE
            binding.topClTitle.text = text
        }
    }



    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commitAllowingStateLoss()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.fragments.size != 1){
            supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewModel = null
        _binding = null
        _uiHelper = null
    }
}