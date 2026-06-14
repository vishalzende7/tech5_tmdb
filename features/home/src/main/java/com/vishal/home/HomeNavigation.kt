package com.vishal.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vishal.home.discovery.DiscoveryScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object DiscoveryTab

@Serializable object Movies

@Serializable object TvShows

@Serializable object PeopleRoute

fun NavGraphBuilder.homeScreen() {
    composable<HomeRoute> {
        HomeScreen()
    }
}