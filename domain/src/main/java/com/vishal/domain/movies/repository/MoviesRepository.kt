package com.vishal.domain.movies.repository

import androidx.paging.PagingData
import com.vishal.domain.movies.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMoviesForHomeScreen(categoryId: String, limit:Int = 20): Flow<List<Movie>>
    suspend fun refreshHomeScreenCategory(categoryId: String)

    // For Category Listing
    fun getPagedMoviesByCategory(categoryId: String, pageSize: Int = 20): Flow<PagingData<Movie>>
}