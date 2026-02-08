package com.myrhstudios.mobileamp.playback

import java.io.File
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

object PlaybackManager {

    fun getPlayer(context: Context): ExoPlayer = ensurePlayer(context)

    private fun File.toMediaItem(): MediaItem = MediaItem.fromUri(this.toURI().toString())

    private var player: ExoPlayer? = null

    fun isPlaying(): Boolean {
        return player?.isPlaying ?: false
    }

    fun clearAndPlay(context: Context, file: File) {
        val player = ensurePlayer(context)
        player.clearMediaItems()
        player.addMediaItem(file.toMediaItem())
        player.prepare()
        player.play()
    }

    fun addToQueue(context: Context, file: File) {
        val player = ensurePlayer(context)
        val wasIdle = !player.isPlaying && player.mediaItemCount == 0

        player.addMediaItem(file.toMediaItem())

        if (wasIdle) {
            player.prepare()
            player.play()
        }
    }

    private fun ensurePlayer(context: Context): ExoPlayer {
        return player ?: ExoPlayer.Builder(context.applicationContext).build().also {
            it.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) {
                        // Media3 will automatically advance to next item if present
                    }
                }
            })
            player = it
        }
    }
}
