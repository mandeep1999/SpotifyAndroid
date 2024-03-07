package com.example.mainactivity.login_module.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mainactivity.R
import com.example.mainactivity.databinding.FragmentLoginBinding
import com.example.mainactivity.login_module.ui.activites.LoginActivity

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

    private fun initialise() {
        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.loginButton.setOnClickListener {
            if (requireActivity() is LoginActivity) {
                (requireActivity() as LoginActivity).showLoginWeb()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            LoginFragment()
    }
}