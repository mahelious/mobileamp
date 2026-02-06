package com.myrhstudios.mobileamp.util

import android.content.Context
import android.os.Environment
import java.io.File

object FileUtils {

    fun getMusicDirectory(context: Context): File? {
        return context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
    }

    fun listAudioFiles(dir: File): List<File> {
        val registered_extensions = setOf("mp3", "m4a", "flac", "ogg", "wav")
        return dir.listFiles()
            ?.filter { it.isFile && it.extension.lowercase() in registered_extensions }
            ?: emptyList()
    }
}
