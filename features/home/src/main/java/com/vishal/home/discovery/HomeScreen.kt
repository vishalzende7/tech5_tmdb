package com.vishal.home.discovery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
import com.vishal.home.discovery.components.MovieCarousel
import com.vishal.home.discovery.components.TVShowCarousel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

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
                HomeTab.Movies -> MoviesContent(state.moviesState)
                HomeTab.TVShows -> TVShowsContent(state.tvShowsState)
                HomeTab.People -> PeopleContent(state.peopleState)
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
            selected = selectedTab == HomeTab.Movies,
            onClick = { onTabSelected(HomeTab.Movies) },
            icon = { Icon(Icons.Default.Movie, contentDescription = "Movies") },
            label = { Text("Movies") }
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
    }
}

@Composable
fun MoviesContent(state: MoviesState) {
    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        )
    } else {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            MovieCarousel(
                title = "Trending",
                movies = state.trending,
                onMovieClick = {},
                modifier = Modifier.padding(bottom = 8.dp)
            )
            MovieCarousel(
                title = "Popular",
                movies = state.popular,
                onMovieClick = {},
                modifier = Modifier.padding(bottom = 8.dp)
            )
            MovieCarousel(title = "Top Rated", movies = state.topRated, onMovieClick = {})
            MovieCarousel(title = "Upcoming", movies = state.upcoming, onMovieClick = {})
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TVShowsContent(state: TVShowsState) {
    if (state.isLoading) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        )
    } else {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            TVShowCarousel(title = "Trending", shows = state.trending, onShowClick = {})
            TVShowCarousel(title = "Popular", shows = state.popular, onShowClick = {})
            TVShowCarousel(title = "Top Rated", shows = state.topRated, onShowClick = {})
            TVShowCarousel(title = "Upcoming", shows = state.upcoming, onShowClick = {})
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PeopleContent(state: PeopleState) {
    // Basic placeholder for People
    Text("People content coming soon", modifier = Modifier.padding(16.dp))
}
