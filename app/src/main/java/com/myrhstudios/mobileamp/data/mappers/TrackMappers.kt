package com.myrhstudios.mobileamp.data.mappers

import java.io.File
import com.myrhstudios.mobileamp.data.db.TrackEntity
import com.myrhstudios.mobileamp.ui.main.MusicListItem

fun TrackEntity.toMusicListItem(): MusicListItem = MusicListItem(
    file = File(path),
    title = title,
    artist = artist,
    album = album,
    duration = duration
)
