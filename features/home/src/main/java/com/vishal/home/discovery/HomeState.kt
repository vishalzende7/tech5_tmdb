package com.vishal.home.discovery

import com.vishal.domain.movies.model.Movie
import com.vishal.domain.shows.model.TVShow
import com.vishal.domain.people.model.Person

data class HomeState(
    val moviesState: MoviesState = MoviesState(),
    val tvShowsState: TVShowsState = TVShowsState(),
    val peopleState: PeopleState = PeopleState(),
    val selectedTab: HomeTab = HomeTab.Movies
)

data class MoviesState(
    val trending: List<Movie> = emptyList(),
    val popular: List<Movie> = emptyList(),
    val topRated: List<Movie> = emptyList(),
    val upcoming: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class TVShowsState(
    val trending: List<TVShow> = emptyList(),
    val popular: List<TVShow> = emptyList(),
    val topRated: List<TVShow> = emptyList(),
    val upcoming: List<TVShow> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

data class PeopleState(
    val popular: List<Person> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

enum class HomeTab {
    Movies, TVShows, People
}
