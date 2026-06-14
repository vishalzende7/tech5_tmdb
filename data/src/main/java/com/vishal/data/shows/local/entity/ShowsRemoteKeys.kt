package com.vishal.data.shows.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.vishal.data.movies.local.entity.MovieEntity

@Entity(
    tableName = "shows_remote_keys",
    primaryKeys = ["showId","categoryId"],
    foreignKeys = [
        ForeignKey(
            entity = TVShowEntity::class,
            parentColumns = ["id"],
            childColumns = ["showId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ShowsRemoteKeys(
    val showId: Int,
    val categoryId: String,
    val nextPage: Int? //next page number to load
)
