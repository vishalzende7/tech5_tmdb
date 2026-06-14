package com.vishal.data.movies.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.vishal.data.database.AppDatabase
import com.vishal.data.movies.local.entity.MovieCategoryMappingEntity
import com.vishal.data.movies.local.entity.MovieEntity
import com.vishal.data.movies.mapper.toDomain
import com.vishal.data.movies.mapper.toEntity
import com.vishal.data.remote.TmdbApiService
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteSource: TmdbApiService,
    private val database: AppDatabase
) : MoviesRepository {
    private val movieDao = database.movieDao()
    override fun getMoviesForHomeScreen(categoryId: String, limit: Int): Flow<List<Movie>> {
        return movieDao.getMoviesForHomeScreen(categoryId, limit).map { e ->
            e.map { it.toDomain() }
        }
    }

    override suspend fun refreshHomeScreenCategory(categoryId: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = when(categoryId) {
                    "trending" -> {
                        remoteSource.getTrendingMovies("day")
                    }
                    "popular" -> {
                        remoteSource.getPopularMovies(1)
                    }
                    "top_rated" -> {
                        remoteSource.getTopRatedMovies(1)
                    }
                    "upcoming" -> {
                        remoteSource.getUpcomingMovies(1)
                    }
                    else -> {
                        throw IllegalArgumentException()
                    }
                }

                database.withTransaction {
                    movieDao.clearMappingsForCategory(categoryId)
                    val nwData = response.results

                    val movieEntities = mutableListOf<MovieEntity>()
                    val mappingEntities = mutableListOf<MovieCategoryMappingEntity>()
                    for((i,e) in nwData.withIndex()) {
                        movieEntities.add(e.toEntity())
                        mappingEntities.add(
                            MovieCategoryMappingEntity(
                                categoryId,
                                e.id,
                                i,
                                title = e.title
                            )
                        )
                    }
                    movieDao.insertMovies(movieEntities)
                    movieDao.insertCategoryMapping(mappingEntities)
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedMoviesByCategory(categoryId: String, pageSize: Int): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = (pageSize / .9).toInt(),
                initialLoadSize = pageSize,
                maxSize = 200,
            ),
            remoteMediator = MovieRemoteMediator(
                remoteSource,
                database,
                categoryId,
                pageSize
            ),
            pagingSourceFactory = {
                database.movieDao().getMoviesForPaging(categoryId)
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}
