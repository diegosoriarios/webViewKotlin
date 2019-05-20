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
import android.provider.Settings.Secure
import android.view.KeyEvent
import com.pushio.manager.PushIOManager
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {

    var mywebview: WebView? = null

    lateinit var cd: CheckNet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cd = CheckNet()
        /*
         * Check network connectivity
         */
        if (cd.isConnectingToInternet(this@MainActivity)) {
            setContentView(R.layout.activity_main)
            mywebview = findViewById<WebView>(R.id.webview)
            mywebview!!.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url)
                    return true
                }
            }



            val androidId:String = Secure.getString(getContentResolver(), Secure.ANDROID_ID)
            val url = "https://admin:admin@ccstore-prod-zdoa.oracleoutsourcing.com"
            val settings = mywebview!!.settings

            settings.domStorageEnabled = true
            mywebview!!.settings.javaScriptEnabled = true

            PushIOManager.getInstance(applicationContext).registerApp()
            PushIOManager.getInstance(applicationContext).registerUserId(androidId)
            Log.d("deviceID", PushIOManager.getInstance(applicationContext).registeredUserId)
            mywebview!!.loadUrl(url)

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
        } else {
            val intent = Intent(this, NoNetwork::class.java)
            startActivity(intent)
            //Toast.makeText(applicationContext,
            //    "no net", Toast.LENGTH_LONG).show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(event?.action == KeyEvent.ACTION_DOWN) {
            when (keyCode) {
                KeyEvent.KEYCODE_BACK ->
                    if(mywebview!!.canGoBack()) {
                        mywebview!!.goBack()
                    } else {
                        finish()
                    }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
