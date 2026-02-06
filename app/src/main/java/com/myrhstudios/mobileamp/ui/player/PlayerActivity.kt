package com.myrhstudios.mobileamp.ui.player

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import com.myrhstudios.mobileamp.R
import com.myrhstudios.mobileamp.databinding.ActivityPlayerBinding
import com.myrhstudios.mobileamp.player.MusicPlayer

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var musicPlayer: MusicPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        musicPlayer = MusicPlayer(this)

        val mediaUri = Uri.parse(
            "android.resource://${packageName}/${R.raw.test_track}"
        )

        val mediaItem = MediaItem.fromUri(mediaUri)

        musicPlayer.setMediaItem(mediaItem)
        binding.playerView.player = musicPlayer.getPlayer()

        musicPlayer.play()
    }

    override fun onStop() {
        super.onStop()
        musicPlayer.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayer.release()
    }

    override fun onBackPressed() {
        musicPlayer.stop()
        super.onBackPressed()
    }
}
