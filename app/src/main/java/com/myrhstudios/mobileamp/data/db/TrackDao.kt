package com.myrhstudios.mobileamp.data.db

import androidx.room.*

@Dao
interface TrackDao {

    // @Query("SELECT * FROM tracks ORDER BY artist COLLATE NOCASE, album COLLATE NOCASE, title COLLATE NOCASE")
    @Query("SELECT * FROM tracks ORDER BY title COLLATE NOCASE")
    suspend fun getAllTracks(): List<TrackEntity>

    @Query("SELECT * FROM tracks WHERE path = :path LIMIT 1")
    suspend fun findByPath(path: String): TrackEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(track: TrackEntity)

    @Query("DELETE FROM tracks WHERE path NOT IN (:paths)")
    suspend fun deleteExcept(paths: List<String>)

}
