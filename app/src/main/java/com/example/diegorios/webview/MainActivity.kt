package com.example.diegorios.webview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.androindian.raj.imei.CheckNet
import kotlinx.android.synthetic.main.activity_main.*
import android.net.http.SslError



class MainActivity : AppCompatActivity() {

    var mywebview: WebView? = null

    lateinit var cd: CheckNet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cd = CheckNet()
        if (cd.isConnectingToInternet(this@MainActivity)) {
            setContentView(R.layout.activity_main)
            mywebview = findViewById<WebView>(R.id.webview)
            mywebview!!.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }

            mywebview!!.settings.javaScriptEnabled = true
            mywebview!!.loadUrl("https://admin:admin@ccstore-prod-zdoa.oracleoutsourcing.com")

        } else {
            Toast.makeText(applicationContext,
                "no net", Toast.LENGTH_LONG).show()
        }

    }
}
