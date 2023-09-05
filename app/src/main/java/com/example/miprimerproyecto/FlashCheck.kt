package com.example.miprimerproyecto

import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class FlashCheck : AppCompatActivity() {

    private lateinit var flashControl: Switch
    private lateinit var cameraManager: CameraManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_check)

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)

        flashControl = findViewById(R.id.switchFlashLight)
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager


        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            // Tiene cámara
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                // Tiene flash
                Toast.makeText(this, "Este dispositivo tiene flash", Toast.LENGTH_SHORT).show()
                flashControl.isEnabled = true
            } else {
                Toast.makeText(this, "Este dispositivo no tiene flash", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Este dispositivo no tiene cámara", Toast.LENGTH_SHORT).show()
        }

        flashControl.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                try {
                    cameraManager.setTorchMode("0", true)
                    flashControl.text = "Flash ON"
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            } else {
                try {
                    cameraManager.setTorchMode("0", false)
                    flashControl.text = "Flash OFF"
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }
        }

        val btnVolver: Button = findViewById(R.id.btnVolverF)

        btnVolver.setOnClickListener() {
            // Apagar el flash si está encendido
            if (flashControl.isChecked) {
                try {
                    cameraManager.setTorchMode("0", false)
                    flashControl.isChecked = false
                    flashControl.text = "Flash OFF"
                } catch (e: CameraAccessException) {
                    e.printStackTrace()
                }
            }

            // Iniciar la nueva actividad
            val intento1 = Intent(this, MainActivity::class.java)
            startActivity(intento1)
        }

    }
}