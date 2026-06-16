package com.vishal.data.movies.mapper

import com.vishal.data.movies.remote.dto.MovieDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieMapperTest {

    @Test
    fun `MovieDto toDomain should map correctly`() {
        // Given
        val dto = MovieDto(
            id = 1,
            title = "Test Movie",
            posterPath = "/path.jpg",
            backdropPath = "/backdrop.jpg",
            releaseDate = "2024-01-01",
            voteAverage = 8.5,
            adult = false,
            overview = "Overview",
            genreIds = listOf(1, 2),
            originalLanguage = "en",
            originalTitle = "Original Title",
            popularity = 100.0,
            softcore = false,
            video = false,
            voteCount = 1000
        )

        // When
        val domain = dto.toDomain()

        // Then
        assertEquals(dto.id, domain.id)
        assertEquals(dto.title, domain.title)
        assertEquals("https://image.tmdb.org/t/p/w500/path.jpg", domain.posterUrl)
        assertEquals("https://image.tmdb.org/t/p/w500/backdrop.jpg", domain.backdropUrl)
        assertEquals(dto.releaseDate, domain.releaseDate)
        assertEquals(dto.voteAverage, domain.rating, 0.0)
    }

    @Test
    fun `MovieDto toDomain with null paths should handle correctly`() {
        // Given
        val dto = MovieDto(
            id = 1,
            title = "Test Movie",
            posterPath = null,
            backdropPath = null,
            releaseDate = null,
            voteAverage = 8.5,
            adult = false,
            overview = "Overview",
            genreIds = listOf(1, 2),
            originalLanguage = "en",
            originalTitle = "Original Title",
            popularity = 100.0,
            softcore = false,
            video = false,
            voteCount = 1000
        )

        // When
        val domain = dto.toDomain()

        // Then
        assertEquals(null, domain.posterUrl)
        assertEquals(null, domain.backdropUrl)
        assertEquals("", domain.releaseDate)
    }
}
