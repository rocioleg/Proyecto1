package com.example.miprimerproyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var flash: Button
    lateinit var camaraf: Button
    lateinit var camarat: Button
    lateinit var gps: Button
    lateinit var auricular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flash = findViewById(R.id.flash)
        camaraf= findViewById(R.id.camaraf)
        camarat= findViewById(R.id.camarat)
        gps= findViewById(R.id.gps)
        auricular= findViewById(R.id.auriculares)


        flash.setOnClickListener {
            val intent = Intent(this, FlashCheck::class.java)
            startActivity(intent)
        }
        camaraf.setOnClickListener {
            val intent = Intent(this, CamaraDCheck::class.java)
            startActivity(intent)
        }
        camarat.setOnClickListener {
            val intent = Intent(this, CamaraTCheck::class.java)
            startActivity(intent)
        }
        gps.setOnClickListener {
            val intent = Intent(this, GPSCheck::class.java)
            startActivity(intent)
        }
        auricular.setOnClickListener {
            val intent = Intent(this, ParlanteCheck::class.java)
            startActivity(intent)
        }
    }

}