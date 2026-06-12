package com.vishal.data.shows.repository

import androidx.paging.PagingData
import com.vishal.domain.core.Resource
import com.vishal.domain.shows.model.TVShow
import com.vishal.domain.shows.model.TVShowDetails
import com.vishal.domain.shows.repository.TVShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TVShowsRepositoryImpl @Inject constructor() : TVShowsRepository {
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

    override suspend fun getTVShowDetails(showId: Int): Resource<TVShowDetails> {
        return Resource.Loading
    }
}