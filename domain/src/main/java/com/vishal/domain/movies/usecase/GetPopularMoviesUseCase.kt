package com.vishal.domain.movies.usecase

import androidx.paging.PagingData
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> = repository.getPopularMovies()
}