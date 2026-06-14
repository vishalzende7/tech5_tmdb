package com.vishal.data.movies.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseTimeStamp: Long,
    val genreIds: List<Int>,
    val genre: List<String>? = null,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val softcore: Boolean,
    val tagline: String? = null,
    val runtime:Int? = null
)
