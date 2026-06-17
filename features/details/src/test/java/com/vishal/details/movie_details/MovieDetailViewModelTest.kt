package com.vishal.details.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.vishal.details.MovieDetailRoute
import com.vishal.details.utils.MainDispatcherRule
import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.MovieDetails
import com.vishal.domain.movies.usecase.GetMovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MovieDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getMovieDetailsUseCase: GetMovieDetailsUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    
    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setUp() {
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandle.toRoute<MovieDetailRoute>() } returns MovieDetailRoute(movieId = 1)
    }

    @Test
    fun `loadMovieDetails should update state with success when use case returns success`() = runTest {
        // Given
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
        coEvery { getMovieDetailsUseCase(1) } returns Resource.Success(movieDetails)

        // When
        viewModel = MovieDetailViewModel(savedStateHandle, getMovieDetailsUseCase)

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(movieDetails, state.movieDetail)
        assertNull(state.errorMessage)
    }

    @Test
    fun `loadMovieDetails should update state with error when use case returns error`() = runTest {
        // Given
        val errorMsg = "Network Error"
        coEvery { getMovieDetailsUseCase(1) } returns Resource.Error(errorMsg)

        // When
        viewModel = MovieDetailViewModel(savedStateHandle, getMovieDetailsUseCase)

        // Then
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.movieDetail)
        assertEquals(errorMsg, state.errorMessage)
    }
}
