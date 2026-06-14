package com.vishal.tmdb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vishal.details.MovieDetailRoute
import com.vishal.details.PersonDetailRoute
import com.vishal.details.TvDetailRoute
import com.vishal.home.HomeRoute
import com.vishal.home.homeScreen
import com.vishal.details.detailFeatureGraph

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ){
        homeScreen(
            onMovieClick = {
                navController.navigate(MovieDetailRoute(it))
            },
            onTvClick = {
                navController.navigate(TvDetailRoute(it))
            },
            onPersonClick = {
                navController.navigate(PersonDetailRoute(it))
            }
        )
        detailFeatureGraph(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}