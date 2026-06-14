package com.vishal.data.shows.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vishal.data.database.AppDatabase
import com.vishal.data.remote.TmdbApiService
import com.vishal.data.shows.local.entity.ShowsRemoteKeys
import com.vishal.data.shows.local.entity.TVShowCategoryMappingEntity
import com.vishal.data.shows.local.entity.TVShowEntity
import com.vishal.data.shows.mapper.toEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class TvShowRemoteMediator(
    private val apiService: TmdbApiService,
    private val database: AppDatabase,
    private val category: String,
    private val pageSize:Int,
) : RemoteMediator<Int, TVShowEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TVShowEntity>
    ): MediatorResult {
        return try {
             val page:Int = getPageForLoadType(loadType, state)
                ?: return MediatorResult.Success(endOfPaginationReached = true)

            val response = when (category) {
                "popular" -> apiService.getPopularShows(page)
                "top_rated" -> apiService.getTopRatedShows(page)
                "upcoming" -> apiService.getUpcomingShows(page)
                else -> throw IllegalArgumentException("Unknown category: $category")
            }
            val networkResult = response.results
            val endOfPaginationReached = networkResult.isEmpty()
            database.withTransaction {
                val isRefresh = loadType == LoadType.REFRESH

                if (isRefresh) {
                    database.tvShowDao().clearMappingsForCategory(category)
                    database.getShowsRemoteKeysDao().clearRemoteKeys(category)
                }
                val startingPosition = (page - 1) * pageSize
                val nextPage = if (endOfPaginationReached) null else page + 1

                val entities = mutableListOf<TVShowEntity>()
                val mappingEntities = mutableListOf<TVShowCategoryMappingEntity>()
                val remoteKeyEntities = mutableListOf<ShowsRemoteKeys>()

                for((i,e) in networkResult.withIndex()) {

                    entities.add(e.toEntity())
                    mappingEntities.add(
                        TVShowCategoryMappingEntity(
                            category,
                            e.id,
                            startingPosition + i,
                            name = e.name
                        )
                    )
                    remoteKeyEntities.add(
                        ShowsRemoteKeys(
                            showId = e.id,
                            categoryId = category,
                            nextPage = nextPage
                        )
                    )
                }

                database.tvShowDao().insertShows(entities)
                database.tvShowDao().insertCategoryMapping(mappingEntities)
                database.getShowsRemoteKeysDao().insertAll(remoteKeyEntities)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }

    }

    private suspend fun getPageForLoadType(
        loadType: LoadType,
        state: PagingState<Int, TVShowEntity>
    ): Int? {
        Log.i("MovieRemoteMediator", "getPageForLoadType is $loadType")
        return when (loadType) {
            // User nav to screen for the first time
            LoadType.REFRESH -> 1

            // User scrolled to the top
            LoadType.PREPEND -> null

            // User scrolled to the bottom. Find the next page.
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return null // If DB is empty, REFRESH will handle it.
                Log.i("MovieRemoteMediator", "Last Item  is ${lastItem.name}")
                // Query the remote keys table for specific category
                val remoteKey = database.getShowsRemoteKeysDao().getRemoteKeysByIdAndCategory(
                    showId = lastItem.id,
                    category = category
                )
                Log.i("MovieRemoteMediator", "Current Remote Key is  is $remoteKey")
                // Return the next page number or null
                remoteKey?.nextPage
            }
        }
    }
}
