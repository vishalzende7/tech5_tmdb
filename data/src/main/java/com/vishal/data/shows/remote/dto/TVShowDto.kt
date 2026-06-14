package com.vishal.data.shows.remote.dto

import com.google.gson.annotations.SerializedName

data class TVShowDto(
    val adult: Boolean,
    val id: Int,
    val name: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("popularity") val popularity: Double,
    val softcore: Boolean,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_count") val voteCount: Int
)


/*
* {
      "adult": false,
      "backdrop_path": "/xLdw1xdHocKYFFvx7w41NchXMfJ.jpg",
      "genre_ids": [
        9648,
        18,
        10765
      ],
      "id": 124364,
      "origin_country": [
        "US"
      ],
      "original_language": "en",
      "original_name": "FROM",
      "overview": "Unravel the mystery of a nightmarish town in middle America that traps all those who enter. As the unwilling residents fight to keep a sense of normalcy and search for a way out, they must also survive the threats of the surrounding forest – including the terrifying creatures that come out when the sun goes down.",
      "popularity": 694.2907,
      "poster_path": "/pRtJagIxpfODzzb0T0NAvZSzErC.jpg",
      "first_air_date": "2022-02-20",
      "softcore": false,
      "name": "FROM",
      "vote_average": 8.465,
      "vote_count": 3613
    }
* */