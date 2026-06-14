package com.vishal.domain.shows.usecase

import androidx.paging.PagingData
import com.vishal.domain.core.Resource
import com.vishal.domain.movies.model.Movie
import com.vishal.domain.movies.repository.MoviesRepository
import com.vishal.domain.shows.model.TVShow
import com.vishal.domain.shows.repository.TVShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTVShowsPagedUseCase @Inject constructor(
    private val repository: TVShowsRepository
) {
    operator fun invoke(category: String): Flow<PagingData<TVShow>> {
        return repository.getPagedShowsByCategory(category)
    }
}
