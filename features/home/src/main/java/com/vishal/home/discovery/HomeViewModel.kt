package com.vishal.home.discovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishal.domain.core.Resource
import com.vishal.domain.movies.repository.MoviesRepository
import com.vishal.domain.movies.usecase.GetPopularMoviesUseCase
import com.vishal.domain.movies.usecase.GetTopRatedMoviesUseCase
import com.vishal.domain.movies.usecase.GetTrendingMoviesUseCase
import com.vishal.domain.movies.usecase.GetUpcomingMoviesUseCase
import com.vishal.domain.shows.usecase.GetTopRatedShowsUseCase
import com.vishal.domain.shows.repository.TVShowsRepository
import com.vishal.domain.people.repository.PeopleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getTopRatedShowsUseCase: GetTopRatedShowsUseCase,
    private val tvShowsRepository: TVShowsRepository,
    //private val peopleRepository: PeopleRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadMovies()
        loadTVShows()
        loadPeople()
    }

    fun onTabSelected(tab: HomeTab) {
        _state.update { it.copy(selectedTab = tab) }
    }

    private fun loadMovies() {
        _state.update { it.copy(moviesState = it.moviesState.copy(isLoading = true)) }
        viewModelScope.launch {
            supervisorScope {
                launch {
                    getTrendingMoviesUseCase().collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        moviesState = it.moviesState.copy(
                                            isLoading = false,
                                            error = result.message
                                        )
                                    )
                                }
                            }

                            Resource.Loading -> {}
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        moviesState = it.moviesState.copy(
                                            isLoading = false,
                                            trending = result.data
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                launch {
                    getPopularMoviesUseCase().collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        moviesState = it.moviesState.copy(
                                            isLoading = false,
                                            error = result.message
                                        )
                                    )
                                }
                            }

                            Resource.Loading -> {}
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        moviesState = it.moviesState.copy(
                                            isLoading = false,
                                            popular = result.data
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                launch {
                    getTopRatedMoviesUseCase().collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        moviesState = it.moviesState.copy(
                                            isLoading = false,
                                            error = result.message
                                        )
                                    )
                                }
                            }

                            Resource.Loading -> {}
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        moviesState = it.moviesState.copy(
                                            isLoading = false,
                                            topRated = result.data
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
                launch {
                    getUpcomingMoviesUseCase().collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        moviesState = it.moviesState.copy(
                                            isLoading = false,
                                            error = result.message
                                        )
                                    )
                                }
                            }

                            Resource.Loading -> {}
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        moviesState = it.moviesState.copy(
                                            isLoading = false,
                                            upcoming = result.data
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }


    }

    private fun loadTVShows() {
        _state.update { it.copy(tvShowsState = it.tvShowsState.copy(isLoading = true)) }
        viewModelScope.launch {
            supervisorScope {
                launch {
                    val trendingDeferred = tvShowsRepository.getTrendingShows("day")

                    when (trendingDeferred) {
                        is Resource.Success -> {
                            _state.update {
                                it.copy(
                                    tvShowsState = it.tvShowsState.copy(
                                        trending = trendingDeferred.data,
                                        popular = trendingDeferred.data.shuffled(), // Placeholder
                                        // topRated = trendingDeferred.data.shuffled(), // Placeholder
                                        upcoming = trendingDeferred.data.shuffled(), // Placeholder
                                        isLoading = false
                                    )
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(
                                    tvShowsState = it.tvShowsState.copy(
                                        isLoading = false,
                                        error = trendingDeferred.message
                                    )
                                )
                            }
                        }

                        Resource.Loading -> {}
                    }
                }
                launch {
                    getTopRatedShowsUseCase().collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        tvShowsState = it.tvShowsState.copy(
                                            isLoading = false,
                                            error = result.message
                                        )
                                    )
                                }
                            }

                            Resource.Loading -> {}
                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        tvShowsState = it.tvShowsState.copy(
                                            isLoading = false,
                                            topRated = result.data
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadPeople() {
        // People only has PagingData in repository.
        // I'll skip it or use a placeholder for now.
    }
}
