package com.example.miprimerproyecto

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class ParlanteCheck : AppCompatActivity() {

    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parlante_check)
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
        Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        stopPlayer()
    }

}