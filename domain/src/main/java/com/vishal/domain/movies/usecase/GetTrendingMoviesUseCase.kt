package com.vishal.domain.movies.usecase

import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(timeWindow: String = "day"): Flow<Resource<List<Movie>>> {
        return repository.getTrendingMovies(timeWindow)
    }
}
