package com.vishal.domain.shows.model

data class TVShowDetails(
    val id: Int,
    val name: String,
    val overview: String,
    val tagline: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val firstAirDate: String,
    val rating: Double,
    val genres: List<String>,
    val numberOfSeasons: Int,
    val numberOfEpisodes: Int
)
