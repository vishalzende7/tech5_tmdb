package com.vishal.data.movies.mapper

import com.vishal.data.movies.local.entity.MovieEntity
import com.vishal.data.movies.remote.dto.MovieDto
import com.vishal.data.utils.getTimeStampFromDateStr
import com.vishal.domain.movies.model.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = releaseDate ?: "",
        rating = voteAverage
    )
}

fun MovieDto.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        releaseTimeStamp = getTimeStampFromDateStr(releaseDate?: ""),
        genreIds = genreIds,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        popularity = popularity,
        softcore = softcore
    )
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w500$it" },
        releaseDate = releaseDate ?: "",
        rating = voteAverage
    )
}
