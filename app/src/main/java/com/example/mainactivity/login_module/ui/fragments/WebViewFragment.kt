package com.example.mainactivity.login_module.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.mainactivity.R
import com.example.mainactivity.databinding.FragmentWebViewBinding
import com.example.mainactivity.login_module.utils.JavaScriptInterface
import com.example.mainactivity.search_module.ui.activities.SearchActivity

class WebViewFragment : Fragment() {

    val KEY_URL: String = "url"

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_view, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseWebView()
    }

    /**
     * Function to initialise the web view, and setup the client.
     */
    private fun initialiseWebView() {
        if (arguments?.getString(KEY_URL) == null) {
            return
        }
        binding.webView.loadUrl(arguments?.getString(KEY_URL)!!)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.settings.databaseEnabled = true
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    _binding?.webView?.visibility = View.VISIBLE
                    _binding?.animatedLoader?.visibility = View.GONE
                } else {
                    _binding?.webView?.visibility = View.GONE
                    _binding?.animatedLoader?.visibility = View.VISIBLE
                }
            }
        }
        WebView.setWebContentsDebuggingEnabled(true)
        binding.webView.webViewClient = WebViewClient()
        binding.webView.addJavascriptInterface(
            JavaScriptInterface(
                context = requireActivity().applicationContext,
                activity = requireActivity(),
                binding.webView,
                ::moveToNextScreen
            ), "app"
        )
    }

    /**
     * Callback function to be invoked after a successfully logging in.
     */
    private fun moveToNextScreen(){
        startActivity(Intent(requireActivity(), SearchActivity::class.java))
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_URL, url)
                }
            }
    }
}