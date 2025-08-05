package com.example.newsweather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.example.newsweather.databinding.ActivitySplashScreenBinding
import com.example.newsweather.databinding.FragmentListingscreenBinding
import com.example.newsweather.databinding.FragmentWebViewBinding

class webView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentWebViewBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_web_view, container, false
        )

        val listScreenFragmentArgs by navArgs<webViewArgs>()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = MyWebViewClient()
        binding.webView.loadUrl(listScreenFragmentArgs.url)
        return binding.root
    }

    private class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url!!)
            return true
        }
    }
}