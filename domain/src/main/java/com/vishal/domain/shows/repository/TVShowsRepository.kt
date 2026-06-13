package com.vishal.domain.shows.repository

import androidx.paging.PagingData
import com.vishal.domain.core.Resource
import com.vishal.domain.shows.model.TVShow
import com.vishal.domain.shows.model.TVShowDetails
import kotlinx.coroutines.flow.Flow

interface TVShowsRepository {
    fun getPopularShows(): Flow<PagingData<TVShow>>
    fun getTopRatedShows(): Flow<PagingData<TVShow>>
    fun getUpcomingShows(): Flow<PagingData<TVShow>> // On The Air in TMDB terms

    suspend fun getTrendingShows(timeWindow: String): Resource<List<TVShow>>
    suspend fun getTopRatedShowsList(page: Int = 1): Flow<Resource<List<TVShow>>>

    suspend fun getTVShowDetails(showId: Int): Resource<TVShowDetails>
}
