package com.vishal.domain.shows.usecase

import com.vishal.domain.core.Resource
import com.vishal.domain.shows.model.TVShow
import com.vishal.domain.shows.repository.TVShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedShowsUseCase @Inject constructor(
    private val repository: TVShowsRepository
) {
    suspend operator fun invoke(page: Int = 1): Flow<Resource<List<TVShow>>> {
        return repository.getTopRatedShowsList(page)
    }
}
