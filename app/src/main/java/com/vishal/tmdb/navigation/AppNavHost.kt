package com.vishal.tmdb.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vishal.home.HomeRoute
import com.vishal.home.homeScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = HomeRoute
    ){
        homeScreen()
    }
}