package com.vishal.domain.movies.usecase

import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.MovieDetails
import com.vishal.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): Resource<MovieDetails?> {
        return repository.getMovieDetailsById(movieId)
    }
}