package com.vishal.home.movie_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vishal.domain.movies.usecase.GetMoviesPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class MovieListingViewModel  @Inject constructor(
    private val getMoviesPagedUseCase: GetMoviesPagedUseCase,
): ViewModel() {

    private val _selectedCategory = MutableStateFlow("popular")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedMovieList = _selectedCategory.flatMapLatest {
        getMoviesPagedUseCase(it)
    }.cachedIn(viewModelScope)

    fun onCategorySelected(newCategory: String) {
        if (_selectedCategory.value != newCategory) {
            _selectedCategory.value = newCategory
        }
    }
}