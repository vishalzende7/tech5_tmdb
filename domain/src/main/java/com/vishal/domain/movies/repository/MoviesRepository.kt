package com.vishal.domain.movies.repository

import androidx.paging.PagingData
import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getTopRatedMovies(): Flow<PagingData<Movie>>
    fun getUpcomingMovies(): Flow<PagingData<Movie>>

    // List fetch for the Home Screen carousels
    suspend fun getTrendingMovies(timeWindow: String): Flow<Resource<List<Movie>>>
    suspend fun getPopularMoviesList(page: Int = 1): Flow<Resource<List<Movie>>>
    suspend fun getTopRatedMoviesList(page: Int = 1): Flow<Resource<List<Movie>>>
    suspend fun getUpcomingMoviesList(page: Int = 1): Flow<Resource<List<Movie>>>

    // Single item fetch for the Details Screen
    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails>
}