package com.vishal.domain.people.repository

import androidx.paging.PagingData
import com.vishal.domain.people.model.Person
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    fun getPopularPeople(): Flow<PagingData<Person>>
}
