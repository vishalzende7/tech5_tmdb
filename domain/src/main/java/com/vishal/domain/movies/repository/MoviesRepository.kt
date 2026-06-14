package com.vishal.domain.movies.repository

import androidx.paging.PagingData
import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getMoviesForHomeScreen(categoryId: String, limit:Int = 20): Flow<List<Movie>>
    suspend fun refreshHomeScreenCategory(categoryId: String)

    // For Category Listing (Vertical Pagination)
    fun getPagedMovies(categoryId: String): Flow<PagingData<Movie>>
}