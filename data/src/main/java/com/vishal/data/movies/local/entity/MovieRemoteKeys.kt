package com.vishal.data.movies.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_remote_keys")
data class MovieRemoteKeys(
    @PrimaryKey val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val category: String
)
