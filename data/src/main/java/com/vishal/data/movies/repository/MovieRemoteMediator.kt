package com.vishal.data.movies.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vishal.data.database.AppDatabase
import com.vishal.data.movies.local.entity.MovieCategoryMappingEntity
import com.vishal.data.movies.local.entity.MovieEntity
import com.vishal.data.movies.local.entity.MovieRemoteKeys
import com.vishal.data.movies.mapper.toEntity
import com.vishal.data.remote.TmdbApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val apiService: TmdbApiService,
    private val database: AppDatabase,
    private val category: String,
    private val pageSize:Int,
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
             val page:Int = getPageForLoadType(loadType, state)
                ?: return MediatorResult.Success(endOfPaginationReached = true)

            val response = when (category) {
                "popular" -> apiService.getPopularMovies(page)
                "top_rated" -> apiService.getTopRatedMovies(page)
                "upcoming" -> apiService.getUpcomingMovies(page)
                else -> throw IllegalArgumentException("Unknown category: $category")
            }
            val networkResult = response.results
            val endOfPaginationReached = networkResult.isEmpty()
            database.withTransaction {
                val isRefresh = loadType == LoadType.REFRESH

                if (isRefresh) {
                    database.movieDao().clearMappingsForCategory(category)
                    database.movieRemoteKeysDao().clearRemoteKeys(category)
                }
                val startingPosition = (page - 1) * pageSize
                val nextPage = if (endOfPaginationReached) null else page + 1

                val movieEntities = mutableListOf<MovieEntity>()
                val mappingEntities = mutableListOf<MovieCategoryMappingEntity>()
                val remoteKeyEntities = mutableListOf<MovieRemoteKeys>()

                for((i,e) in networkResult.withIndex()) {
                    movieEntities.add(e.toEntity())
                    mappingEntities.add(
                        MovieCategoryMappingEntity(
                            category,
                            e.id,
                            startingPosition + i,
                            title = e.title
                        )
                    )
                    remoteKeyEntities.add(
                        MovieRemoteKeys(
                            movieId = e.id,
                            categoryId = category,
                            nextPage = nextPage
                        )
                    )
                }

                database.movieDao().insertMovies(movieEntities)
                database.movieDao().insertCategoryMapping(mappingEntities)
                database.movieRemoteKeysDao().insertAll(remoteKeyEntities)
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
        state: PagingState<Int, MovieEntity>
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
                Log.i("MovieRemoteMediator", "Last Item  is ${lastItem.title}")
                // Query the remote keys table for specific category
                val remoteKey = database.movieRemoteKeysDao().getRemoteKeysByIdAndCategory(
                    movieId = lastItem.id,
                    category = category
                )
                Log.i("MovieRemoteMediator", "Current Remote Key is  is $remoteKey")
                // Return the next page number or null
                remoteKey?.nextPage
            }
        }
    }
}
