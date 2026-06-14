package com.vishal.home.tv_shows

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vishal.home.discovery.TVShowsState
import com.vishal.home.discovery.components.TVShowCarousel

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