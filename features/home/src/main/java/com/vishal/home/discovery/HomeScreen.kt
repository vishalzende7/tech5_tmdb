package com.vishal.home.discovery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vishal.domain.movies.model.Movie
import com.vishal.home.discovery.components.MovieCarousel
import com.vishal.home.discovery.components.TVShowCarousel
import com.vishal.home.movie_list.MovieListContent
import com.vishal.home.peoples.PeopleContent
import com.vishal.home.tv_shows.TVShowsContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val trendingMovies by viewModel.trendingMovies.collectAsState()

    Scaffold(
        bottomBar = {
            HomeBottomNavigation(
                selectedTab = state.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (state.selectedTab) {
                HomeTab.Discovery -> HomeContent(state.moviesState, trendingMovies)
                HomeTab.TVShows -> TVShowsContent(state.tvShowsState)
                HomeTab.People -> PeopleContent(state.peopleState)
                HomeTab.MovieList -> MovieListContent(
                    state = state.movieListState,
                    onCategorySelected = viewModel::onCategorySelected
                )
            }
        }
    }
}

@Composable
fun HomeBottomNavigation(
    selectedTab: HomeTab,
    onTabSelected: (HomeTab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == HomeTab.Discovery,
            onClick = { onTabSelected(HomeTab.Discovery) },
            icon = { Icon(Icons.Default.Movie, contentDescription = "Home") },
            label = { Text("Discover") }
        )
        NavigationBarItem(
            selected = selectedTab == HomeTab.TVShows,
            onClick = { onTabSelected(HomeTab.TVShows) },
            icon = { Icon(Icons.Default.Tv, contentDescription = "TV Shows") },
            label = { Text("TV Shows") }
        )
        NavigationBarItem(
            selected = selectedTab == HomeTab.People,
            onClick = { onTabSelected(HomeTab.People) },
            icon = { Icon(Icons.Default.Person, contentDescription = "People") },
            label = { Text("People") }
        )
        NavigationBarItem(
            selected = selectedTab == HomeTab.MovieList,
            onClick = { onTabSelected(HomeTab.MovieList) },
            icon = { Icon(Icons.Default.List, contentDescription = "Movie List") },
            label = { Text("Movie List") }
        )
    }
}

@Composable
fun HomeContent(
    state: MoviesState,
    trending: List<Movie>,
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
                onMovieClick = {},
                onRefreshClick = viewModel::refreshTrendingListing,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            MovieCarousel(
                title = "Popular",
                movies = pop,
                onMovieClick = {},
                modifier = Modifier.padding(bottom = 8.dp),
                onRefreshClick = viewModel::refreshPopularListing,
            )
            MovieCarousel(
                title = "Top Rated",
                movies = topRated,
                onMovieClick = {},
                onRefreshClick = viewModel::refreshTopRatedListing,
            )
            MovieCarousel(
                title = "Upcoming",
                movies = upcomingMovies,
                onMovieClick = {},
                onRefreshClick = viewModel::refreshUpcomingListing,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

