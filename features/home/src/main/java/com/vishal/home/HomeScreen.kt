package com.vishal.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vishal.home.discovery.DiscoveryScreen
import com.vishal.home.movie_list.MovieListScreen
import com.vishal.home.peoples.PeopleContent
import com.vishal.home.tv_shows.TVShowListingScreen

@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onTvClick: (Int) -> Unit,
    onPersonClick: (Int) -> Unit
) {
    val homeNavController = rememberNavController()

    Scaffold(
        bottomBar = { HomeBottomBar(homeNavController) } // Your bottom bar goes here
    ) { innerPadding ->

        // The Nested NavHost for the Tabs
        NavHost(
            navController = homeNavController,
            startDestination = DiscoveryTab,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<DiscoveryTab> {
                DiscoveryScreen(
                    onMovieClick = onMovieClick
                )
            }
            composable<Movies> {
                MovieListScreen(
                    onMovieClick = onMovieClick
                )
            }
            composable<TvShows> {
                TVShowListingScreen(
                    onTvClick = onTvClick
                )
            }
            composable<PeopleRoute> {
                PeopleContent()
            }
        }
    }
}
private data class HomeTabItem(
    val title: String,
    val icon: ImageVector,
    val route: Any // Accepts @Serializable route objects
)

@Composable
internal fun HomeBottomBar(navController: NavHostController) {
    // Defines the 4 tabs representing the core sections required by the assignment
    val tabs = listOf(
        HomeTabItem("Discover", Icons.Default.Explore, DiscoveryTab),
        HomeTabItem("Movies", Icons.Default.Movie, Movies),
        HomeTabItem("TV Shows", Icons.Default.Tv, TvShows),
        HomeTabItem("People", Icons.Default.People, PeopleRoute)
    )

    NavigationBar {
        // Observe the current back stack to determine which tab is active
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        tabs.forEach { tab ->
            // check if the simpleName exists within that generated route string.
            val isSelected = currentDestination?.hierarchy?.any {
                it.route?.contains(tab.route::class.simpleName ?: "") == true
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(tab.route) {
                        // Pop up to the start destination of the graph to avoid building up a large back stack
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when re-selecting the same tab
                        launchSingleTop = true
                        // Restore state when re-selecting a previously selected item (preserves scroll position!)
                        restoreState = false
                    }
                },
                icon = { Icon(imageVector = tab.icon, contentDescription = tab.title) },
                label = { Text(text = tab.title) }
            )
        }
    }
}