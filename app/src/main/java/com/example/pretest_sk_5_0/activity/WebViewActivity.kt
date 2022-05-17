package com.example.pretest_sk_5_0.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.pretest_sk_5_0.R
import com.example.pretest_sk_5_0.databinding.ActivityWebViewBinding
import com.example.pretest_sk_5_0.utils.Utils

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // make webview display in screen and enable javascript
        binding.apply {
            webView.webViewClient = WebViewClient()
            webView.settings.setSupportZoom(true)
            webView.settings.javaScriptEnabled = true
            val url = Utils.getPdfUrl()
            webView.loadUrl("https://docs.google.com/gview?embedded=true&url=$url")
        }
    }
}