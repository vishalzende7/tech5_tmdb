package com.vishal.data.movies.remote

import com.vishal.data.movies.remote.dto.PaginatedResponseDto
import com.vishal.data.movies.remote.dto.MovieDto
import com.vishal.data.movies.remote.dto.TVShowDto
import com.vishal.data.movies.remote.dto.PersonDto

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("trending/movie/{time_window}")
    suspend fun getTrendingMovies(
        @Path("time_window") timeWindow: String,
        @Query("language") language: String = "en-US"
    ): PaginatedResponseDto<MovieDto>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1
    ): PaginatedResponseDto<MovieDto>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1
    ): PaginatedResponseDto<MovieDto>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1
    ): PaginatedResponseDto<MovieDto>

    @GET("trending/tv/{time_window}")
    suspend fun getTrendingShows(
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int = 1
    ): PaginatedResponseDto<TVShowDto>

    @GET("tv/popular")
    suspend fun getPopularShows(
        @Query("page") page: Int = 1
    ): PaginatedResponseDto<TVShowDto>

    @GET("tv/top_rated")
    suspend fun getTopRatedShows(
        @Query("page") page: Int = 1
    ): PaginatedResponseDto<TVShowDto>

    @GET("tv/on_the_air")
    suspend fun getUpcomingShows(
        @Query("page") page: Int = 1
    ): PaginatedResponseDto<TVShowDto>

    @GET("person/popular")
    suspend fun getPopularPeople(
        @Query("page") page: Int = 1
    ): PaginatedResponseDto<PersonDto>
}
