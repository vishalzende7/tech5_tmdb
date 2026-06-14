package com.vishal.data.shows.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import com.vishal.data.movies.local.entity.MovieEntity

@Entity(
    "show_category_mapping",
    primaryKeys = ["categoryId","showId"],
    foreignKeys = [
        ForeignKey(
            entity = TVShowEntity::class,
            parentColumns = ["id"],
            childColumns = ["showId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TVShowCategoryMappingEntity (
    val categoryId: String, // e.g., "trending", "popular"
    val showId: Int,
    val position: Int, //Preserves order from API
    val name:String?
)