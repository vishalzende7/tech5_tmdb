package com.vishal.data.shows.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vishal.data.shows.local.entity.TVShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TVShowDao {
    @Query("SELECT * FROM tv_shows WHERE isTrending = 1 ORDER BY voteAverage DESC")
    fun getTrendingShows(): Flow<List<TVShowEntity>>

    @Query("SELECT * FROM tv_shows WHERE isPopular = 1 ORDER BY voteAverage DESC")
    fun getPopularShows(): Flow<List<TVShowEntity>>

    @Query("SELECT * FROM tv_shows WHERE isTopRated = 1 ORDER BY voteAverage DESC")
    fun getTopRatedShows(): Flow<List<TVShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShows(shows: List<TVShowEntity>)

    @Query("UPDATE tv_shows SET isTrending = 0")
    suspend fun clearTrendingStatus()

    @Query("UPDATE tv_shows SET isPopular = 0")
    suspend fun clearPopularStatus()

    @Query("UPDATE tv_shows SET isTopRated = 0")
    suspend fun clearTopRatedStatus()

    @Query("DELETE FROM tv_shows WHERE isTrending = 0 AND isPopular = 0 AND isTopRated = 0")
    suspend fun deleteUnusedShows()
}
