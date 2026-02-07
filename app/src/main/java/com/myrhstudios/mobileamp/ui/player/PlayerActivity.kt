package com.myrhstudios.mobileamp.ui.player

import java.io.File
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import com.myrhstudios.mobileamp.databinding.ActivityPlayerBinding
import com.myrhstudios.mobileamp.player.MusicPlayer

class PlayerActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_AUDIO_PATH = "extra_audio_path"
    }

    private lateinit var binding: ActivityPlayerBinding

    private lateinit var musicPlayer: MusicPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        musicPlayer = MusicPlayer(this)

        val audioPath = intent.getStringExtra(EXTRA_AUDIO_PATH)
            ?: run {
                finish()
                return
            }

        musicPlayer.setMediaItem(MediaItem.fromUri(Uri.fromFile(File(audioPath))))
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

    @Deprecated("onBackPressed is no longer called for back gestures; migrate to AndroidX's backward compatible OnBackPressedDispatcher")
    override fun onBackPressed() {
        musicPlayer.stop()
        super.onBackPressed()
    }
}
