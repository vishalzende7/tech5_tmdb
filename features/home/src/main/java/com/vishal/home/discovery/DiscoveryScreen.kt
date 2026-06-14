package com.vishal.home.discovery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vishal.domain.movies.model.Movie
import com.vishal.home.discovery.components.MovieCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val trendingMovies by viewModel.trendingMovies.collectAsState()

    DiscoveryContent(state.moviesState, trendingMovies, onMovieClick)
}

@Composable
fun DiscoveryContent(
    state: MoviesState,
    trending: List<Movie>,
    onMovieClick: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val pop by viewModel.popularMovies.collectAsState()
    val topRated by viewModel.topRatedMovies.collectAsState()
    val upcomingMovies by viewModel.upcomingMovies.collectAsState()

    if (trending.isEmpty()) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        )
    } else {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            MovieCarousel(
                title = "Trending",
                movies = trending,
                onMovieClick = {
                    onMovieClick(it.id)
                },
                onRefreshClick = viewModel::refreshTrendingListing,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            MovieCarousel(
                title = "Popular",
                movies = pop,
                onMovieClick = {
                    onMovieClick(it.id)
                },
                modifier = Modifier.padding(bottom = 8.dp),
                onRefreshClick = viewModel::refreshPopularListing,
            )
            MovieCarousel(
                title = "Top Rated",
                movies = topRated,
                onMovieClick = {
                    onMovieClick(it.id)
                },
                onRefreshClick = viewModel::refreshTopRatedListing,
            )
            MovieCarousel(
                title = "Upcoming",
                movies = upcomingMovies,
                onMovieClick = {
                    onMovieClick(it.id)
                },
                onRefreshClick = viewModel::refreshUpcomingListing,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

