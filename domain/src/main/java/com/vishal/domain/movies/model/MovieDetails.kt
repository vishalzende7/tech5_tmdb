package com.vishal.domain.movies.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val tagline: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String,
    val rating: Double,
    val runtime: Int,
    val genres: List<String>
)
