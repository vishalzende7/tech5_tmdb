package com.vishal.data.movies.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.paging.PagingSource
import com.vishal.data.movies.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM trending WHERE isTrending = 1 ORDER BY voteAverage DESC")
    fun getTrendingMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM trending WHERE isPopular = 1 ORDER BY voteAverage DESC")
    fun getPopularMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM trending WHERE isTopRated = 1 ORDER BY voteAverage DESC")
    fun getTopRatedMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM trending WHERE isUpcoming = 1 ORDER BY voteAverage DESC")
    fun getUpcomingMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM trending WHERE isPopular = 1")
    fun getPopularMoviesPaged(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM trending WHERE isTopRated = 1")
    fun getTopRatedMoviesPaged(): PagingSource<Int, MovieEntity>

    @Query("SELECT * FROM trending WHERE isUpcoming = 1")
    fun getUpcomingMoviesPaged(): PagingSource<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("UPDATE trending SET isTrending = 0")
    suspend fun clearTrendingStatus()

    @Query("UPDATE trending SET isPopular = 0")
    suspend fun clearPopularStatus()

    @Query("UPDATE trending SET isTopRated = 0")
    suspend fun clearTopRatedStatus()

    @Query("UPDATE trending SET isUpcoming = 0")
    suspend fun clearUpcomingStatus()

    @Query("DELETE FROM trending WHERE isTrending = 0 AND isPopular = 0 AND isTopRated = 0 AND isUpcoming = 0")
    suspend fun deleteUnusedMovies()
}
