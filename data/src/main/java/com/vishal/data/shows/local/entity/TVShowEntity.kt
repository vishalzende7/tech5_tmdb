package com.vishal.data.shows.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_shows")
data class TVShowEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val firstAirDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseTimeStamp: Long,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalName: String,
    val popularity: Double,
    val softcore: Boolean,
)
