package com.example.miprimerproyecto

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog

class GPSCheckMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gpscheck_main)

        val botonCheck: Button = findViewById(R.id.btnCheckGPSMain)
        val botonVerMapa: Button = findViewById(R.id.btnVerMapa)
        val botonVolver: Button = findViewById(R.id.btnVolverCGM)

        botonCheck.setOnClickListener(){
            if(checkIfGPSIsEnabled()){ run {
                val alertDialog1 = AlertDialog.Builder(this)
                    // Establecer ícono
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    // Establecer título
                    .setTitle("Mensaje")
                    // Establecer mensaje
                    .setMessage("Su dispositivo cuenta con GPS")
                    // Establecer botón positivo
                    .setPositiveButton("Ok") { dialogInterface, _ ->
                        // Lo que sucederá cuando se haga clic en el botón positivo
                        botonVerMapa.visibility = View.VISIBLE
                        botonVerMapa.setEnabled(true)
                        botonVerMapa.setBackgroundColor(Color.GREEN)
                    }.show()
            }}else{ run {
                val alertDialog2 = AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Mensaje")
                    .setMessage("Su dispositivo NO cuenta con GPS")
                    .setPositiveButton("Ok") { dialogInterface, _ ->
                        botonVerMapa.visibility = View.VISIBLE
                        botonVerMapa.setEnabled(false)
                        botonVerMapa.setBackgroundColor(Color.RED)
                    }.show()
            }}
        }

        botonVolver.setOnClickListener(){
            val intento1 = Intent(this, MainActivity::class.java)
            startActivity(intento1)
        }

        botonVerMapa.setOnClickListener(){
            val intento1 = Intent(this, GPSCheck::class.java)
            startActivity(intento1)
        }


    }

    fun checkIfGPSIsEnabled(): Boolean {

        val myPackageManager: PackageManager = packageManager
        val bolGPSSupported: Boolean

        bolGPSSupported = myPackageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION);

        return bolGPSSupported
    }

    override fun onBackPressed(){
        val intento1 = Intent(this, MainActivity::class.java)
        startActivity(intento1)
    }

}