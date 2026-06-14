package com.vishal.data.movies.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.vishal.data.database.AppDatabase
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
    private val category: String
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyAtClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = when (category) {
                "popular" -> apiService.getPopularMovies(page)
                "top_rated" -> apiService.getTopRatedMovies(page)
                "upcoming" -> apiService.getUpcomingMovies(page)
                else -> throw IllegalArgumentException("Unknown category: $category")
            }

            val movies = response.results
            val endOfPaginationReached = movies.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.movieRemoteKeysDao().clearRemoteKeys(category)
                    // We don't clear the whole table because it might contain other categories or trending
                    // Instead, we might want to clear based on category status if we had that in MovieDao
                    // For now, following the simple approach
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    MovieRemoteKeys(id = it.id, prevPage = prevKey, nextPage = nextKey, category = category)
                }
                database.movieRemoteKeysDao().insertAll(keys)
                
                val entities = movies.map { dto ->
                    dto.toEntity(

                    )
                }
                database.movieDao().insertMovies(entities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieEntity>): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                database.movieRemoteKeysDao().getRemoteKeysByIdAndCategory(movie.id, category)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                database.movieRemoteKeysDao().getRemoteKeysByIdAndCategory(movie.id, category)
            }
    }

    private suspend fun getRemoteKeyAtClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.movieRemoteKeysDao().getRemoteKeysByIdAndCategory(id, category)
            }
        }
    }
}
