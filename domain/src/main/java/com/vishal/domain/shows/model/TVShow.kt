package com.vishal.domain.shows.model

data class TVShow(
    val id: Int,
    val name: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val firstAirDate: String,
    val rating: Double
)
