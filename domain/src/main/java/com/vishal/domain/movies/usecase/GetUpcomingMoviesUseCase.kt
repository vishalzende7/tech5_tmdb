package com.vishal.domain.movies.usecase

import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(page: Int = 1): Flow<Resource<List<Movie>>> {
        return repository.getUpcomingMoviesList(page)
    }
}
