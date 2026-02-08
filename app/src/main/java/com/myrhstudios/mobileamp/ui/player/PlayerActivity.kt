package com.myrhstudios.mobileamp.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
import com.myrhstudios.mobileamp.databinding.ActivityPlayerBinding
import com.myrhstudios.mobileamp.playback.PlaybackManager

class PlayerActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_AUDIO_PATH = "extra_audio_path"
    }

    private lateinit var binding: ActivityPlayerBinding

    @androidx.annotation.OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val exoPlayer = PlaybackManager.getPlayer(this)
        binding.playerView.player = exoPlayer
        // when player view opens show the controller right away
        // this may be undesirable as the Player layout matures
        binding.playerView.showController()
    }

    override fun onStop() {
        super.onStop()
        val exoPlayer = PlaybackManager.getPlayer(this)
        exoPlayer.pause()
    }
}
