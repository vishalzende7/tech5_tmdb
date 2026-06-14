package com.vishal.domain.movies.usecase

import com.vishal.domain.movies.repository.MoviesRepository
import javax.inject.Inject

class RefreshUpcomingMovies @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke() {
        repository.refreshHomeScreenCategory("upcoming")
    }
}