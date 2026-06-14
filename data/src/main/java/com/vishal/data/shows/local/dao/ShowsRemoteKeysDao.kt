package com.vishal.data.shows.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vishal.data.movies.local.entity.MovieRemoteKeys
import com.vishal.data.shows.local.entity.ShowsRemoteKeys

@Dao
interface ShowsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ShowsRemoteKeys>)

    @Query("SELECT * FROM shows_remote_keys WHERE showId = :showId AND categoryId = :category")
    suspend fun getRemoteKeysByIdAndCategory(showId: Int, category: String): ShowsRemoteKeys?

    @Query("DELETE FROM shows_remote_keys WHERE categoryId = :category")
    suspend fun clearRemoteKeys(category: String)
}
