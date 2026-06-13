package com.vishal.data.people.repository

import androidx.paging.PagingData
import com.vishal.domain.people.model.Person
import com.vishal.domain.people.repository.PeopleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor() : PeopleRepository {
    override fun getPopularPeople(): Flow<PagingData<Person>> {
        return flow {  }
    }
}