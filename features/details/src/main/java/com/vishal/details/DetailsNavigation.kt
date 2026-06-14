package com.vishal.details

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.vishal.details.movie_details.MovieDetailScreen
import kotlinx.serialization.Serializable

// 1. The Public Routes
@Serializable data class MovieDetailRoute(val movieId: Int)
@Serializable data class TvDetailRoute(val seriesId: Int)
@Serializable data class PersonDetailRoute(val personId: Int)

fun NavGraphBuilder.detailFeatureGraph(
    onBackClick: () -> Unit
) {
    composable<MovieDetailRoute> {
        val r = it.toRoute<MovieDetailRoute>()
        Text("Screen MovieDetailRoute details with ${r.movieId}")
        MovieDetailScreen(
            onBackClick = onBackClick,
        )
    }

    composable<TvDetailRoute> {
        val r = it.toRoute<TvDetailRoute>()
        Text("Screen TvDetailRoute details with ${r.seriesId}")
    }

    composable<PersonDetailRoute> {
        val r = it.toRoute<PersonDetailRoute>()
        Text("Screen PersonDetailRoute details with ${r.personId}")
    }
}