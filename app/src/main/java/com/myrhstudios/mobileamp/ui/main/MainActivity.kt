package com.myrhstudios.mobileamp.ui.main

import android.os.Bundle
import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.myrhstudios.mobileamp.databinding.ActivityMainBinding
import com.myrhstudios.mobileamp.ui.player.PlayerActivity
import com.myrhstudios.mobileamp.util.FileUtils
import com.myrhstudios.mobileamp.util.MetadataUtils

class MainActivity : androidx.appcompat.app.AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MusicListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // to prevent screen dimming while playing
        // ... except playerView doesn't exist just yet LOL
        // binding.playerView.keepScreenOn = true

        setContentView(binding.root)

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


        // Playback will come back later

        adapter = MusicListAdapter { item ->
            val intent = Intent(this, PlayerActivity::class.java).apply {
                putExtra(PlayerActivity.EXTRA_AUDIO_PATH, item.file.absolutePath)
            }
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter

        adapter.submitList(items)

    }
}
