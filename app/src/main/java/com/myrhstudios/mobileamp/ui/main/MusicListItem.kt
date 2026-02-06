package com.myrhstudios.mobileamp.ui.main

import java.io.File

data class MusicListItem(
    val file: File,
    val artist: String? = null,
    val album: String? = null,
    val title: String,
    val duration: Long? = null
)
