package com.vishal.data.shows.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.vishal.data.database.AppDatabase
import com.vishal.data.remote.TmdbApiService
import com.vishal.data.shows.local.entity.TVShowCategoryMappingEntity
import com.vishal.data.shows.local.entity.TVShowEntity
import com.vishal.data.shows.mapper.toDomain
import com.vishal.data.shows.mapper.toEntity
import com.vishal.domain.shows.model.TVShow
import com.vishal.domain.shows.repository.TVShowsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TVShowsRepositoryImpl @Inject constructor(
    private val remoteSource: TmdbApiService,
    private val database: AppDatabase
) : TVShowsRepository {
    private val showsDao = database.tvShowDao()
    override fun getShowsForHomeScreen(
        categoryId: String,
        limit: Int
    ): Flow<List<TVShow>> {
        return showsDao.getShowsForHomeScreen("categoryId").map { e ->
            e.map { it.toDomain() }
        }
    }

    override suspend fun refreshHomeScreenCategory(categoryId: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = when(categoryId) {
                    "trending" -> {
                        remoteSource.getTrendingShows("day")
                    }
                    "popular" -> {
                        remoteSource.getPopularShows(1)
                    }
                    "top_rated" -> {
                        remoteSource.getTopRatedShows(1)
                    }
                    "upcoming" -> {
                        remoteSource.getUpcomingShows(1)
                    }
                    else -> {
                        throw IllegalArgumentException()
                    }
                }

                database.withTransaction {
                    showsDao.clearMappingsForCategory(categoryId)
                    val nwData = response.results

                    val entities = mutableListOf<TVShowEntity>()
                    val mappingEntities = mutableListOf<TVShowCategoryMappingEntity>()
                    for((i,e) in nwData.withIndex()) {
                        entities.add(e.toEntity())
                        mappingEntities.add(
                            TVShowCategoryMappingEntity(
                                categoryId,
                                e.id,
                                i,
                                name = e.name
                            )
                        )
                    }
                    showsDao.insertShows(entities)
                    showsDao.insertCategoryMapping(mappingEntities)
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedShowsByCategory(
        categoryId: String,
        pageSize: Int
    ): Flow<PagingData<TVShow>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = (pageSize / .9).toInt(),
                initialLoadSize = pageSize,
                maxSize = 200,
            ),
            remoteMediator = TvShowRemoteMediator(
                remoteSource,
                database,
                categoryId,
                pageSize
            ),
            pagingSourceFactory = {
                database.tvShowDao().getShowsForPaging(categoryId)
            }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }
}
