package com.vishal.domain.movies.usecase

import app.cash.turbine.test
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.repository.MoviesRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetPopularMoviesForHomeScreenUseCaseTest {

    private lateinit var getPopularMoviesUseCase: GetPopularMoviesForHomeScreenUseCase
    private val moviesRepository: MoviesRepository = mockk()

    @Before
    fun setUp() {
        getPopularMoviesUseCase = GetPopularMoviesForHomeScreenUseCase(moviesRepository)
    }

    @Test
    fun `invoke should return movies flow from repository`() = runTest {
        // Given
        val movies = listOf(
            Movie(
                id = 1,
                title = "Movie 1",
                posterUrl = null,
                backdropUrl = null,
                releaseDate = "2024-01-01",
                rating = 8.0
            )
        )
        every { moviesRepository.getMoviesForHomeScreen("popular") } returns flowOf(movies)

        // When & Then
        getPopularMoviesUseCase().test {
            assertEquals(movies, awaitItem())
            awaitComplete()
        }
    }
}
