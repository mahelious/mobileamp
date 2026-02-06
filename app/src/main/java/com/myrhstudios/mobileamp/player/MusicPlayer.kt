package com.myrhstudios.mobileamp.player

import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class MusicPlayer(context: Context) {

    private val player: ExoPlayer = ExoPlayer.Builder(context).build()

    fun getPlayer(): ExoPlayer = player

    fun setMediaItem(mediaItem: MediaItem) {
        player.setMediaItem(mediaItem)
        // prepare() buffers and gets ready
        player.prepare()
    }

    // non-negotiable
    fun release() {
        player.release()
    }

    fun play() {
        // starts playback
        player.playWhenReady = true
    }

    fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
    }
}
