package com.vishal.data.movies.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    "movie_category_mapping",
    primaryKeys = ["categoryId","movieId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieCategoryMappingEntity (
    val categoryId: String, // e.g., "trending", "popular"
    val movieId: Int,
    val position: Int, //Preserves order from API
    val title:String?
)