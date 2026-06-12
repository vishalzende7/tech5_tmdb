package com.vishal.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vishal.home.discovery.HomeScreen
import kotlinx.serialization.Serializable

// 1. Define a type-safe route object
@Serializable
object HomeRoute

// 2. Expose an extension function to let the :app module register this screen
fun NavGraphBuilder.homeScreen() {
    composable<HomeRoute> {
        // Hilt ViewModel injection happens locally inside the feature module
        HomeScreen()
    }
}