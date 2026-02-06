package com.myrhstudios.mobileamp.util

import android.media.MediaMetadataRetriever
import com.myrhstudios.mobileamp.ui.main.MusicListItem
import java.io.File

object MetadataUtils {

    fun extractMetadata(file: File): MusicListItem {
        val retriever = MediaMetadataRetriever()

        return try {
            retriever.setDataSource(file.absolutePath)

            val title =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
                    ?: file.nameWithoutExtension

            val artist =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)

            val album =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)

            val durationMs =
                retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
                    ?.toLongOrNull()

            MusicListItem(
                file = file,
                title = title,
                artist = artist,
                album = album,
                duration = durationMs?.div(1000)
            )
        } finally {
            retriever.release()
        }
    }
}
