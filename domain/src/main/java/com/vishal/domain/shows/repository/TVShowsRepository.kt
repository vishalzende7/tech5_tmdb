package com.vishal.domain.shows.repository

import androidx.paging.PagingData
import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.shows.model.TVShow
import com.vishal.domain.shows.model.TVShowDetails
import kotlinx.coroutines.flow.Flow

interface TVShowsRepository {
    fun getShowsForHomeScreen(categoryId: String, limit:Int = 20): Flow<List<TVShow>>
    suspend fun refreshHomeScreenCategory(categoryId: String)

    // For Category Listing
    fun getPagedShowsByCategory(categoryId: String, pageSize: Int = 20): Flow<PagingData<TVShow>>
}
