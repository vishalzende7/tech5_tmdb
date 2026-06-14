package com.vishal.data.movies.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vishal.data.movies.local.entity.MovieRemoteKeys

@Dao
interface MovieRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<MovieRemoteKeys>)

    @Query("SELECT * FROM movie_remote_keys WHERE movieId = :movieId AND categoryId = :category")
    suspend fun getRemoteKeysByIdAndCategory(movieId: Int, category: String): MovieRemoteKeys?

    @Query("DELETE FROM movie_remote_keys WHERE categoryId = :category")
    suspend fun clearRemoteKeys(category: String)
}
