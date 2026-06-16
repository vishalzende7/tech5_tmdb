package com.vishal.data.movies.repository

import com.vishal.data.database.AppDatabase
import com.vishal.data.movies.local.dao.MovieDao
import com.vishal.data.movies.local.entity.MovieEntity
import com.vishal.data.remote.TmdbApiService
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesRepositoryImplTest {

    private lateinit var repository: MoviesRepositoryImpl
    private val remoteSource: TmdbApiService = mockk()
    private val database: AppDatabase = mockk()
    private val movieDao: MovieDao = mockk()

    @Before
    fun setUp() {
        every { database.movieDao() } returns movieDao
        repository = MoviesRepositoryImpl(remoteSource, database)
    }

    @Test
    fun `getMoviesForHomeScreen should return domain movies from DAO`() = runTest {
        // Given
        val categoryId = "popular"
        val limit = 20
        val movieEntities = listOf(
            MovieEntity(
                id = 1,
                title = "Test Movie",
                overview = "Overview",
                posterPath = "/path.jpg",
                backdropPath = "/backdrop.jpg",
                releaseDate = "2024-01-01",
                voteAverage = 8.5,
                voteCount = 100,
                releaseTimeStamp = 123456789L,
                genreIds = listOf(1),
                originalLanguage = "en",
                originalTitle = "Original",
                popularity = 10.0,
                softcore = false
            )
        )
        every { movieDao.getMoviesForHomeScreen(categoryId, limit) } returns flowOf(movieEntities)

        // When
        val result = repository.getMoviesForHomeScreen(categoryId, limit).first()

        // Then
        assertEquals(1, result.size)
        assertEquals(movieEntities[0].id, result[0].id)
        assertEquals(movieEntities[0].title, result[0].title)
    }
}
