package com.vishal.data.shows.mapper

import com.vishal.data.movies.remote.dto.TVShowDto
import com.vishal.data.shows.local.entity.TVShowEntity
import com.vishal.domain.shows.model.TVShow

fun TVShowDto.toDomain(): TVShow {
    return TVShow(
        id = id,
        name = name,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        firstAirDate = firstAirDate ?: "",
        rating = voteAverage
    )
}

fun TVShowDto.toEntity(
    isTrending: Boolean = false,
    isPopular: Boolean = false,
    isTopRated: Boolean = false
): TVShowEntity {
    return TVShowEntity(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        firstAirDate = firstAirDate,
        voteAverage = voteAverage,
        isTrending = isTrending,
        isPopular = isPopular,
        isTopRated = isTopRated
    )
}

fun TVShowEntity.toDomain(): TVShow {
    return TVShow(
        id = id,
        name = name,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        firstAirDate = firstAirDate ?: "",
        rating = voteAverage
    )
}
