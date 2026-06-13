package com.vishal.data.movies.remote.dto

import com.google.gson.annotations.SerializedName

data class PaginatedResponseDto<T>(
    val page: Int,
    val results: List<T>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)
