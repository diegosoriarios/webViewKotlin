package com.example.diegorios.webview

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_conection.*

class NoNetwork : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_conection)

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            refresh()
        }
    }

    fun refresh() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}