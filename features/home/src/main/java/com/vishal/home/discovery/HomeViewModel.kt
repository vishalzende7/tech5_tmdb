package com.vishal.home.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vishal.domain.core.Resource
import com.vishal.domain.movies.usecase.GetMoviesPagedUseCase
import com.vishal.domain.movies.usecase.GetPopularMoviesForHomeScreenUseCase
import com.vishal.domain.movies.usecase.GetTopRatedMoviesForHomeScreenUseCase
import com.vishal.domain.movies.usecase.GetTrendingMoviesForHomeScreenUseCase
import com.vishal.domain.movies.usecase.GetUpcomingMoviesForHomeScreenUseCase
import com.vishal.domain.movies.usecase.RefreshPopularMovies
import com.vishal.domain.movies.usecase.RefreshTopRatedMovies
import com.vishal.domain.movies.usecase.RefreshTrendingMovies
import com.vishal.domain.movies.usecase.RefreshUpcomingMovies
import com.vishal.domain.shows.usecase.GetTopRatedShowsUseCase
import com.vishal.domain.shows.repository.TVShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesForHomeScreenUseCase,
    private val refreshTrendingMoviesUseCase: RefreshTrendingMovies,
    private val getPopularMoviesForHomeScreenUseCase: GetPopularMoviesForHomeScreenUseCase,
    private val refreshPopularMoviesUseCase: RefreshPopularMovies,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesForHomeScreenUseCase,
    private val refreshTopRatedMovies: RefreshTopRatedMovies,
    private val getUpcomingMoviesForHomeScreenUseCase: GetUpcomingMoviesForHomeScreenUseCase,
    private val refreshUpcomingMovies: RefreshUpcomingMovies,
    private val getMoviesPagedUseCase: GetMoviesPagedUseCase,
    private val getTopRatedShowsUseCase: GetTopRatedShowsUseCase,
    private val tvShowsRepository: TVShowsRepository, //fixme: remove repo
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()
    val trendingMovies = getTrendingMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily,emptyList())

    val popularMovies = getPopularMoviesForHomeScreenUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily,emptyList())

    val topRatedMovies = getTopRatedMoviesUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily,emptyList())

    val upcomingMovies = getUpcomingMoviesForHomeScreenUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily,emptyList())

    private val _selectedCategory = MutableStateFlow("popular")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedMovieList = _selectedCategory.flatMapLatest {
        getMoviesPagedUseCase(it)
    }.cachedIn(viewModelScope)

    init {
        refreshHomeScreenListing()
    }

    fun refreshHomeScreenListing() = viewModelScope.launch {
        supervisorScope {
            refreshTrendingMoviesUseCase()
            refreshPopularMoviesUseCase()
            refreshTopRatedMovies()
            refreshUpcomingMovies()
        }
    }

    fun refreshTrendingListing() {
        viewModelScope.launch {
            refreshTrendingMoviesUseCase()
        }
    }

    fun refreshPopularListing() {
        viewModelScope.launch {
            refreshPopularMoviesUseCase()
        }
    }

    fun refreshTopRatedListing() {
        viewModelScope.launch {
            refreshTopRatedMovies()
        }
    }

    fun refreshUpcomingListing() {
        viewModelScope.launch {
            refreshUpcomingMovies()
        }
    }

    fun onTabSelected(tab: HomeTab) {
        _state.update { it.copy(selectedTab = tab) }
    }

    fun onCategorySelected(newCategory: String) {
        if (_selectedCategory.value != newCategory) {
            _selectedCategory.value = newCategory
        }
    }
}
