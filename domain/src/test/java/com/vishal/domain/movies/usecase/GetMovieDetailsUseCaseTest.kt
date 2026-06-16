package com.vishal.domain.movies.usecase

import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.MovieDetails
import com.vishal.domain.movies.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMovieDetailsUseCaseTest {

    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase
    private val moviesRepository: MoviesRepository = mockk()

    @Before
    fun setUp() {
        getMovieDetailsUseCase = GetMovieDetailsUseCase(moviesRepository)
    }

    @Test
    fun `invoke should return movie details from repository when successful`() = runTest {
        // Given
        val movieId = 1
        val movieDetails = MovieDetails(
            id = 1,
            title = "Test Movie",
            overview = "Overview",
            tagline = "Tagline",
            posterUrl = null,
            backdropUrl = null,
            releaseDate = "2024-01-01",
            rating = 8.0,
            runtime = 120,
            genres = listOf("Action")
        )
        val expectedResource = Resource.Success(movieDetails)
        coEvery { moviesRepository.getMovieDetailsById(movieId) } returns expectedResource

        // When
        val result = getMovieDetailsUseCase(movieId)

        // Then
        assertEquals(expectedResource, result)
    }

    @Test
    fun `invoke should return error from repository when failure occurs`() = runTest {
        // Given
        val movieId = 1
        val expectedResource = Resource.Error("Something went wrong")
        coEvery { moviesRepository.getMovieDetailsById(movieId) } returns expectedResource

        // When
        val result = getMovieDetailsUseCase(movieId)

        // Then
        assertEquals(expectedResource, result)
    }
}
