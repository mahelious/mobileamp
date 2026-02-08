package com.myrhstudios.mobileamp.data.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "tracks",
    indices = [
        Index(value = ["path"], unique = true),
        Index(value = ["artist"]),
        Index(value = ["album"])
    ]
)

data class TrackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val path: String,
    val fileSize: Long,
    val lastModified: Long,

    val title: String,
    val artist: String?,
    val album: String?,
    val duration: Long?
)
