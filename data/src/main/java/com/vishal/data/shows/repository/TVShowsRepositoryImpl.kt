package com.vishal.data.shows.repository

import androidx.paging.PagingData
import com.vishal.data.remote.TmdbApiService
import com.vishal.data.shows.local.dao.TVShowDao
import com.vishal.data.shows.mapper.toDomain
import com.vishal.data.shows.mapper.toEntity
import com.vishal.domain.core.Resource
import com.vishal.domain.shows.model.TVShow
import com.vishal.domain.shows.model.TVShowDetails
import com.vishal.domain.shows.repository.TVShowsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TVShowsRepositoryImpl @Inject constructor(
    private val remoteSource: TmdbApiService,
    private val tvShowDao: TVShowDao
) : TVShowsRepository {
    override fun getPopularShows(): Flow<PagingData<TVShow>> {
        return flow {  }
    }

    override fun getTopRatedShows(): Flow<PagingData<TVShow>> {
        return flow {  }
    }

    override fun getUpcomingShows(): Flow<PagingData<TVShow>> {
        return flow {  }
    }

    override suspend fun getTrendingShows(timeWindow: String): Resource<List<TVShow>> {
        return Resource.Loading
    }

    override suspend fun getTopRatedShowsList(page: Int): Flow<Resource<List<TVShow>>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = remoteSource.getTopRatedShows(page)
                val entities = response.results.map { it.toEntity(isTopRated = true) }

                // Update local cache
                tvShowDao.clearTopRatedStatus()
                tvShowDao.insertShows(entities)
                tvShowDao.deleteUnusedShows()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            tvShowDao.getTopRatedShows().transform { entities ->
                emit(Resource.Success(entities.map { it.toDomain() }))
            }
        }
    }

    override suspend fun getTVShowDetails(showId: Int): Resource<TVShowDetails> {
        return Resource.Loading
    }
}
