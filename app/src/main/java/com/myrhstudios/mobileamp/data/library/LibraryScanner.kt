package com.myrhstudios.mobileamp.data.library

import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.Context
import android.os.Environment
import com.myrhstudios.mobileamp.data.db.TrackDao
import com.myrhstudios.mobileamp.data.db.TrackEntity
import com.myrhstudios.mobileamp.util.MetadataUtils

class LibraryScanner(
    private val context: Context,
    private val trackDao: TrackDao
) {

    fun listAudioFiles(dir: File): List<File> {
        val registeredExtensions = setOf("mp3", "m4a", "flac", "ogg", "wav")
        return dir.walkTopDown()
            .filter { it.isFile && it.extension.lowercase() in registeredExtensions }
            .toList()
    }

    suspend fun scan() = withContext(Dispatchers.IO) {
        val audioFiles = listAudioFiles(
            context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
            ?: return@withContext
        )

        val seenPaths = mutableListOf<String>()

        for (trackFile in audioFiles) {
            seenPaths += trackFile.absolutePath

            val trackCache = trackDao.findByPath(trackFile.absolutePath)

            if (trackCache != null &&
                trackCache.fileSize == trackFile.length() &&
                trackCache.lastModified == trackFile.lastModified()) continue

            val meta = MetadataUtils.extractMetadata(trackFile)

            val newTrackCache = TrackEntity(
                path = trackFile.absolutePath,
                fileSize = trackFile.length(),
                lastModified = trackFile.lastModified(),
                title = meta.title,
                artist = meta.artist,
                album = meta.album,
                duration = meta.duration
            )

            trackDao.upsert(newTrackCache)
        }

        trackDao.deleteExcept(seenPaths)
    }

}
