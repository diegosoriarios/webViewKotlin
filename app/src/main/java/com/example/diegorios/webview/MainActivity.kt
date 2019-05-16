package com.example.diegorios.webview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.androindian.raj.imei.CheckNet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import android.R.id.message
import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient




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

            val url = "https://diegosoriarios.github.io"
            val settings = mywebview!!.settings
            settings.domStorageEnabled = true

            //Console.log
            mywebview!!.webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                    Log.d(
                        "MyApplication", consoleMessage.message() + " -- From line "
                                + consoleMessage.lineNumber() + " of "
                                + consoleMessage.sourceId()
                    )
                    return super.onConsoleMessage(consoleMessage)
                }
            }

            mywebview!!.loadUrl(url)

        } else {
            val intent = Intent(this, NoNetwork::class.java)
            startActivity(intent)
            //Toast.makeText(applicationContext,
            //    "no net", Toast.LENGTH_LONG).show()
        }

    }
}
