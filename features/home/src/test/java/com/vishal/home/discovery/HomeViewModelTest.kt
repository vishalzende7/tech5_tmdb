package com.vishal.home.discovery

import com.vishal.domain.movies.usecase.*
import com.vishal.domain.shows.usecase.GetTopRatedShowsUseCase
import com.vishal.domain.shows.repository.TVShowsRepository
import com.vishal.home.utils.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getTrendingMoviesUseCase: GetTrendingMoviesForHomeScreenUseCase = mockk()
    private val refreshTrendingMoviesUseCase: RefreshTrendingMovies = mockk(relaxed = true)
    private val getPopularMoviesUseCase: GetPopularMoviesForHomeScreenUseCase = mockk()
    private val refreshPopularMoviesUseCase: RefreshPopularMovies = mockk(relaxed = true)
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesForHomeScreenUseCase = mockk()
    private val refreshTopRatedMovies: RefreshTopRatedMovies = mockk(relaxed = true)
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesForHomeScreenUseCase = mockk()
    private val refreshUpcomingMovies: RefreshUpcomingMovies = mockk(relaxed = true)
    private val getMoviesPagedUseCase: GetMoviesPagedUseCase = mockk()
    private val getTopRatedShowsUseCase: GetTopRatedShowsUseCase = mockk()
    private val tvShowsRepository: TVShowsRepository = mockk()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        every { getTrendingMoviesUseCase() } returns flowOf(emptyList())
        every { getPopularMoviesUseCase() } returns flowOf(emptyList())
        every { getTopRatedMoviesUseCase() } returns flowOf(emptyList())
        every { getUpcomingMoviesUseCase() } returns flowOf(emptyList())
        every { getMoviesPagedUseCase(any()) } returns flowOf(mockk())

        viewModel = HomeViewModel(
            getTrendingMoviesUseCase,
            refreshTrendingMoviesUseCase,
            getPopularMoviesUseCase,
            refreshPopularMoviesUseCase,
            getTopRatedMoviesUseCase,
            refreshTopRatedMovies,
            getUpcomingMoviesUseCase,
            refreshUpcomingMovies,
            getMoviesPagedUseCase,
            getTopRatedShowsUseCase,
            tvShowsRepository
        )
    }

    @Test
    fun `init should refresh all listings`() = runTest {
        // Then
        coVerify { refreshTrendingMoviesUseCase() }
        coVerify { refreshPopularMoviesUseCase() }
        coVerify { refreshTopRatedMovies() }
        coVerify { refreshUpcomingMovies() }
    }

    @Test
    fun `onCategorySelected should update selectedCategory state`() = runTest {
        // When
        val newCategory = "top_rated"
        viewModel.onCategorySelected(newCategory)

        // Then
        assertEquals(newCategory, viewModel.selectedCategory.value)
    }

    @Test
    fun `onTabSelected should update selectedTab in state`() = runTest {
        // When
        val newTab = HomeTab.TVShows
        viewModel.onTabSelected(newTab)

        // Then
        assertEquals(newTab, viewModel.state.value.selectedTab)
    }
}
