package com.vishal.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object DiscoveryTab

@Serializable object Movies

@Serializable object TvShows

@Serializable object PeopleRoute

fun NavGraphBuilder.homeScreen(
    onMovieClick: (Int) -> Unit,
    onTvClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit
) {
    composable<HomeRoute> {
        HomeScreen(
            onMovieClick = onMovieClick,
            onTvClick = onTvClick,
            onPersonClick = onPersonClick
        )
    }
}