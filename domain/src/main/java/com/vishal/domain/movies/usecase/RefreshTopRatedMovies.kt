package com.vishal.domain.movies.usecase

import com.vishal.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class RefreshTopRatedMovies @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke() {
        repository.refreshHomeScreenCategory("top_rated")
    }
}