package com.vishal.domain.movies.usecase

import com.vishal.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class RefreshTrendingMovies @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke() {
        repository.refreshHomeScreenCategory("trending")
    }
}