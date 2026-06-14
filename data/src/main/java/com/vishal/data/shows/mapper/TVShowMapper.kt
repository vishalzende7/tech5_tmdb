package com.vishal.data.shows.mapper

import com.vishal.data.shows.remote.dto.TVShowDto
import com.vishal.data.shows.local.entity.TVShowEntity
import com.vishal.data.utils.getTimeStampFromDateStr
import com.vishal.domain.shows.model.TVShow

fun TVShowDto.toEntity(): TVShowEntity {
    return TVShowEntity(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        firstAirDate = firstAirDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseTimeStamp = getTimeStampFromDateStr(firstAirDate?: ""),
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalName = originalName,
        popularity = popularity,
        softcore = softcore
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


