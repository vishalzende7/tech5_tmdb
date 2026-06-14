package com.vishal.data.movies.repository

import androidx.paging.PagingData
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

    override fun getPagedMovies(categoryId: String): Flow<PagingData<Movie>> {
        TODO("Not yet implemented")
    }

//    @OptIn(ExperimentalPagingApi::class)
//    override fun getPopularMovies(pageSize: Int): Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(pageSize = pageSize),
//            remoteMediator = MovieRemoteMediator(remoteSource, database, "popular"),
//            pagingSourceFactory = { movieDao.getPopularMoviesPaged() }
//        ).flow.transform { pagingData ->
//            emit(pagingData.map { it.toDomain() })
//        }
//    }
//
//    @OptIn(ExperimentalPagingApi::class)
//    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(pageSize = 20),
//            remoteMediator = MovieRemoteMediator(remoteSource, database, "top_rated"),
//            pagingSourceFactory = { movieDao.getTopRatedMoviesPaged() }
//        ).flow.transform { pagingData ->
//            emit(pagingData.map { it.toDomain() })
//        }
//    }
//
//    @OptIn(ExperimentalPagingApi::class)
//    override fun getUpcomingMovies(): Flow<PagingData<Movie>> {
//        return Pager(
//            config = PagingConfig(pageSize = 20),
//            remoteMediator = MovieRemoteMediator(remoteSource, database, "upcoming"),
//            pagingSourceFactory = { movieDao.getUpcomingMoviesPaged() }
//        ).flow.transform { pagingData ->
//            emit(pagingData.map { it.toDomain() })
//        }
//    }
//
//    override suspend fun getTrendingMovies(timeWindow: String): Flow<Resource<List<Movie>>> {
//        withContext(Dispatchers.IO) {
//            try {
//                val response = remoteSource.getTrendingMovies(timeWindow)
//                val entities = response.results.map { it.toEntity(isTrending = true) }
//
//                // Update local cache
//                movieDao.clearTrendingStatus()
//                movieDao.insertMovies(entities)
//                movieDao.deleteUnusedMovies()
//            } catch (e: Exception) {
//                // If network fails, try to get from cache
//                e.printStackTrace()
//            }
//
//        }
//        return movieDao.getTrendingMovies().transform { entities ->
//            emit(Resource.Success(entities.map { it.toDomain() }))
//        }
//    }
//
//    override suspend fun getPopularMoviesList(page: Int): Flow<Resource<List<Movie>>> {
//        return withContext(Dispatchers.IO) {
//            try {
//                Log.i("VISHAL", "getPopularMoviesList called")
//                val response = remoteSource.getPopularMovies(page)
//                val entities = response.results.map { it.toEntity(isPopular = true) }
//
//                // Update local cache
//                movieDao.clearPopularStatus()
//                movieDao.insertMovies(entities)
//                movieDao.deleteUnusedMovies()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            movieDao.getPopularMovies().transform { entities ->
//                emit(Resource.Success(entities.map { it.toDomain() }))
//            }
//        }
//    }
//
//    override suspend fun getTopRatedMoviesList(page: Int): Flow<Resource<List<Movie>>> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = remoteSource.getTopRatedMovies(page)
//                val entities = response.results.map { it.toEntity(isTopRated = true) }
//
//                // Update local cache
//                movieDao.clearTopRatedStatus()
//                movieDao.insertMovies(entities)
//                movieDao.deleteUnusedMovies()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            movieDao.getTopRatedMovies().transform { entities ->
//                emit(Resource.Success(entities.map { it.toDomain() }))
//            }
//        }
//    }
//
//    override suspend fun getUpcomingMoviesList(page: Int): Flow<Resource<List<Movie>>> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = remoteSource.getUpcomingMovies(page)
//                val entities = response.results.map { it.toEntity(isUpcoming = true) }
//
//                // Update local cache
//                movieDao.clearUpcomingStatus()
//                movieDao.insertMovies(entities)
//                movieDao.deleteUnusedMovies()
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//            movieDao.getUpcomingMovies().transform { entities ->
//                emit(Resource.Success(entities.map { it.toDomain() }))
//            }
//        }
//    }
//
//    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
//        return withContext(Dispatchers.IO) {
//            try {
//                //val response = remoteSource.getMovieDetails(movieId)
//                Resource.Loading
//            } catch (e: Exception) {
//                Resource.Error("Failed to fetch movie details", e)
//            }
//        }
//    }
}
