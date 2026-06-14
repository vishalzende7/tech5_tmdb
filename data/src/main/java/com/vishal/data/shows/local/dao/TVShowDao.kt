package com.vishal.data.shows.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.vishal.data.movies.local.entity.MovieCategoryMappingEntity
import com.vishal.data.movies.local.entity.MovieEntity
import com.vishal.data.shows.local.entity.TVShowCategoryMappingEntity
import com.vishal.data.shows.local.entity.TVShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TVShowDao {
    @Query(
        """
        SELECT t.* FROM tv_shows t 
        INNER JOIN show_category_mapping mcm ON t.id = mcm.showId 
        WHERE mcm.categoryId = :categoryId 
        ORDER BY mcm.position ASC 
        LIMIT :limit
    """
    )
    fun getShowsForHomeScreen(categoryId: String, limit: Int = 20): Flow<List<TVShowEntity>>

    @Query(
        """
    SELECT t.* FROM tv_shows t 
    INNER JOIN show_category_mapping mcm ON t.id = mcm.showId 
    WHERE mcm.categoryId = :categoryId 
    ORDER BY mcm.position ASC
    """
    )
    fun getShowsForPaging(categoryId: String): PagingSource<Int, TVShowEntity>

    //Write Queries
    @Upsert
    suspend fun insertShows(movies: List<TVShowEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryMapping(mappings: List<TVShowCategoryMappingEntity>)


    //Clearing Data
    @Query("DELETE FROM show_category_mapping WHERE categoryId = :categoryId")
    suspend fun clearMappingsForCategory(categoryId: String)

}
