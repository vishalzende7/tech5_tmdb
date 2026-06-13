package com.vishal.data.movies.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trending")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val releaseTimeStamp: Long,
    val isTrending: Boolean = false,
    val isPopular: Boolean = false,
    val isTopRated: Boolean = false,
    val isUpcoming: Boolean = false
)
