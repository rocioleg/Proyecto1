package com.example.miprimerproyecto

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.checkFlash){
            val intent = Intent(this, FlashCheck::class.java)
            startActivity(intent)
        } else
        if (id == R.id.checkCD){
            val intent = Intent(this, CamaraDCheck::class.java)
            startActivity(intent)
        } else
        if (id == R.id.checkCT){
            val intent = Intent(this, CamaraTCheck::class.java)
            startActivity(intent)
        } else
        if (id == R.id.checkGps){
            val intent = Intent(this, GPSCheck::class.java)
            startActivity(intent)
        } else
        if (id == R.id.checkVolumen){
            val intent = Intent(this, ParlanteCheck::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}