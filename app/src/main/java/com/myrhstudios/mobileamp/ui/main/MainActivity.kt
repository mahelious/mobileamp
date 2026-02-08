package com.myrhstudios.mobileamp.ui.main

import android.os.Bundle
import android.content.Intent

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myrhstudios.mobileamp.data.db.AppDatabase
import com.myrhstudios.mobileamp.data.library.LibraryScanner
import com.myrhstudios.mobileamp.data.mappers.toMusicListItem
import com.myrhstudios.mobileamp.databinding.ActivityMainBinding
import com.myrhstudios.mobileamp.ui.player.PlayerActivity
import kotlinx.coroutines.launch

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

        // 1. initiate RecyclerView + Adapter wiring
        adapter = MusicListAdapter { item ->
            val intent = Intent(this, PlayerActivity::class.java).apply {
                putExtra(PlayerActivity.EXTRA_AUDIO_PATH, item.file.absolutePath)
            }
            startActivity(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // 2. load track-cache database contents immediately
        val db = AppDatabase.get(this)
        val trackDao = db.trackDao()
        lifecycleScope.launch {
            val tracks = trackDao.getAllTracks()
            adapter.submitList(tracks.map { it.toMusicListItem() })
        }

        // 3. kick-off incremental scan in background
        lifecycleScope.launch {
            val scanner = LibraryScanner(this@MainActivity, trackDao)
            scanner.scan()

            val tracks = trackDao.getAllTracks()
            adapter.submitList(tracks.map { it.toMusicListItem() })
        }
        // This means the metadata cache is only sync'd on app start
        // TODO - listener to Music/ events should auto-sync file-system changes to the metadata cache
    }
}
