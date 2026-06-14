package com.vishal.data.movies.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val adult: Boolean,
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("softcore") val softcore: Boolean,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_count") val voteCount: Int
)


/*
* {
    "adult": false,
    "backdrop_path": "/zMwhWailP1WY7sb6AoE6b8ugoy.jpg",
    "genre_ids": [
        12,
        16,
        10751,
        14
    ],
    "id": 1007757,
    "original_language": "en",
    "original_title": "Swapped",
    "overview": "A small woodland creature and a majestic bird, two natural sworn enemies of the Valley, magically trade places and set off on an adventure of a lifetime to switch back. Their journey soon uncovers a greater threat—one that could endanger not only their species, but the entire valley they call home.",
    "popularity": 174.8725,
    "poster_path": "/tHhxWxge06goXU6ZQH1hj7vK8Hd.jpg",
    "release_date": "2026-05-01",
    "softcore": false,
    "title": "Swapped",
    "video": false,
    "vote_average": 8.961,
    "vote_count": 1665
}
* */