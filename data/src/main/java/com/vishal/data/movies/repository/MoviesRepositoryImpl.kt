package com.vishal.data.movies.repository

import androidx.paging.PagingData
import com.vishal.data.movies.local.dao.MovieDao
import com.vishal.data.movies.mapper.toDomain
import com.vishal.data.movies.mapper.toEntity
import com.vishal.data.movies.remote.TmdbApiService
import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.model.MovieDetails
import com.vishal.domain.movies.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val remoteSource: TmdbApiService,
    private val movieDao: MovieDao
): MoviesRepository {
    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return flow {  }
    }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return flow {  }
    }

    override fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return flow {  }
    }

    override suspend fun getTrendingMovies(timeWindow: String): Flow<Resource<List<Movie>>> {
         return withContext(Dispatchers.IO) {
            try {
                val response = remoteSource.getTrendingMovies(timeWindow)
                val entities = response.results.map { it.toEntity(isTrending = true) }
                
                // Update local cache
                movieDao.clearTrendingStatus()
                movieDao.insertMovies(entities)
            } catch (e: Exception) {
                // If network fails, try to get from cache
                e.printStackTrace()
            }
             movieDao.getTrendingMovies().transform { entities ->
                 emit(Resource.Success(entities.map { it.toDomain() }))
             }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetails> {
        return withContext(Dispatchers.IO) {
            try {
                //val response = remoteSource.getMovieDetails(movieId)
                Resource.Loading
            } catch (e: Exception) {
                Resource.Error("Failed to fetch movie details", e)
            }
        }
    }
}
