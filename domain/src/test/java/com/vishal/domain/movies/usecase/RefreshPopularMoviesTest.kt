package com.vishal.domain.movies.usecase

import com.vishal.domain.movies.repository.MoviesRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RefreshPopularMoviesTest {

    private lateinit var refreshPopularMovies: RefreshPopularMovies
    private val moviesRepository: MoviesRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        refreshPopularMovies = RefreshPopularMovies(moviesRepository)
    }

    @Test
    fun `invoke should call refreshHomeScreenCategory with popular`() = runTest {
        // When
        refreshPopularMovies()

        // Then
        coVerify { moviesRepository.refreshHomeScreenCategory("popular") }
    }
}
