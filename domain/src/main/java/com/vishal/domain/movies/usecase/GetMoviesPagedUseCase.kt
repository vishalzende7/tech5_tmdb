package com.vishal.domain.movies.usecase

import androidx.paging.PagingData
import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesPagedUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(category: String): Flow<Resource<PagingData<Movie>>> {
        return flow{}
//        val flow = when (category.lowercase()) {
//            "popular" -> repository.getPopularMovies()
//            "top_rated" -> repository.getTopRatedMovies()
//            "upcoming" -> repository.getUpcomingMovies()
//            else -> throw IllegalArgumentException("Unknown category: $category")
//        }
//        return flow.map { Resource.Success(it) }
    }
}
