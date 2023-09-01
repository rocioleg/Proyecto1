package com.example.miprimerproyecto

import android.content.Context
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class FlashCheck : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_check)
    }

    val CameraManager : CameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager


}