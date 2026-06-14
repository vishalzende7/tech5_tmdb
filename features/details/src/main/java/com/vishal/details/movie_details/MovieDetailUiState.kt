package com.vishal.details.movie_details

import com.vishal.domain.movies.model.MovieDetails

data class MovieDetailUiState(
    val isLoading: Boolean = true,
    val movieDetail: MovieDetails? = null,
    val errorMessage: String? = null
)
