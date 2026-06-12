package com.vishal.data.movies.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vishal.data.movies.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE isTrending = 1 ORDER BY voteAverage DESC")
    fun getTrendingMovies(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("UPDATE movies SET isTrending = 0")
    suspend fun clearTrendingStatus()

    @Query("DELETE FROM movies WHERE isTrending = 0")
    suspend fun clearNonTrendingMovies()
}
