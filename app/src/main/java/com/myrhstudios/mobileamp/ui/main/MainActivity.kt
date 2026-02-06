package com.myrhstudios.mobileamp.ui.main

import android.os.Bundle
import android.content.Intent
import com.myrhstudios.mobileamp.ui.player.PlayerActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.myrhstudios.mobileamp.databinding.ActivityMainBinding
import com.myrhstudios.mobileamp.util.FileUtils
import com.myrhstudios.mobileamp.util.MetadataUtils

class MainActivity : androidx.appcompat.app.AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // to prevent screen dimming while playing
        // ... except playerView doesn't exist just yet LOL
        // binding.playerView.keepScreenOn = true

        setContentView(binding.root)

        /*
        startActivity(
            Intent(this, PlayerActivity::class.java)
        )
         */
        val musicDir = FileUtils.getMusicDirectory(this)
        val files = musicDir?.let { FileUtils.listAudioFiles(it) } ?: emptyList()

        val items = files
            .map {
                MetadataUtils.extractMetadata(it)
            }
            .sortedWith(
                compareBy(
                    { it.artist ?: "ZZZ" },
                    { it.album ?: "ZZZ" },
                    { it.title }
                )
            )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MusicListAdapter(items) {
            // Playback will come back later
        }
    }
}
