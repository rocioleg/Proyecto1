package com.example.miprimerproyecto

import android.content.Intent
import android.graphics.Color
import android.hardware.camera2.CameraAccessException
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class ParlanteCheck : AppCompatActivity() {

    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parlante_check)

        val btnPlay: Button = findViewById(R.id.btnPlay)
        val btnPause: Button = findViewById(R.id.btnPause)
        val btnStop: Button = findViewById(R.id.btnStop)

        val textView: TextView = findViewById(R.id.mensaje)

        val btnVolver: Button = findViewById(R.id.btnVolverAURI)

        btnVolver.setOnClickListener() {
            val intento1 = Intent(this, MainActivity::class.java)
            startActivity(intento1)
        }

        if(auricularesConectados()){
            textView.text ="¡Auriculares detectados!"
            btnPlay.setEnabled(true)
            btnPlay.setBackgroundColor(Color.GREEN)
            btnPause.setEnabled(true)
            btnPause.setBackgroundColor(Color.GREEN)
            btnStop.setEnabled(true)
            btnStop.setBackgroundColor(Color.GREEN)
        } else {
            textView.text ="¡Auriculares NO detectados!"
            btnPlay.setEnabled(false)
            btnPlay.setBackgroundColor(Color.RED)
            btnPause.setEnabled(false)
            btnPause.setBackgroundColor(Color.RED)
            btnStop.setEnabled(false)
            btnStop.setBackgroundColor(Color.RED)
        }

    }

    fun play(v: View) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.himno)
            player?.setOnCompletionListener {
                stopPlayer()
            }
        }
        player?.start()
    }

    fun pause(v: View) {
        player?.pause()
    }

    fun stop(v: View) {
        stopPlayer()
    }

    private fun stopPlayer() {
        player?.release()
        player = null
        Toast.makeText(this, "Se detuvo el audio", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }

    private fun auricularesConectados(): Boolean {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager // crea una instancia de AM
        val audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_INPUTS) // obtiene informacion sobre los dispositivos de entrada de audio disponibles
        for (deviceInfo in audioDevices) { // itera en esa lista
            if (deviceInfo.type == AudioDeviceInfo.TYPE_WIRED_HEADPHONES // busca si hay auriculares conectados (sin microf)
                || deviceInfo.type == AudioDeviceInfo.TYPE_WIRED_HEADSET // busca si hay auriculares conectados (con microf)
            ) {
                return true
            }
        }
        return false
    }

}