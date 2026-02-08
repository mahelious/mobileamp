package com.myrhstudios.mobileamp.ui.main

import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myrhstudios.mobileamp.R
import com.myrhstudios.mobileamp.data.db.AppDatabase
import com.myrhstudios.mobileamp.data.library.LibraryScanner
import com.myrhstudios.mobileamp.data.mappers.toMusicListItem
import com.myrhstudios.mobileamp.databinding.ActivityMainBinding
import com.myrhstudios.mobileamp.ui.player.PlayerActivity
import com.myrhstudios.mobileamp.playback.PlaybackManager
import kotlinx.coroutines.launch

class MainActivity : androidx.appcompat.app.AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MusicListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // 1. initiate RecyclerView + Adapter wiring
        adapter = MusicListAdapter(
            onItemClick = { item ->
                PlaybackManager.clearAndPlay(this, item.file)
                val intent = Intent(this, PlayerActivity::class.java)
                startActivity(intent)
            },
            onItemLongClick = { view, item ->
                showQueueMenu(view, item)
            }
        )

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
    private fun showQueueMenu(anchor: View, item: MusicListItem) {
        PopupMenu(this, anchor).apply {
            menu.add(this@MainActivity.getString(R.string.menu_lbl_addqueue))
            setOnMenuItemClickListener {
                PlaybackManager.addToQueue(this@MainActivity, item.file)
                true
            }
            show()
        }
    }
}
