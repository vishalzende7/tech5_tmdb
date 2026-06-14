package com.vishal.data.movies.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.paging.PagingSource
import androidx.room.Upsert
import com.vishal.data.movies.local.entity.MovieCategoryMappingEntity
import com.vishal.data.movies.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("""
        SELECT m.* FROM movies m 
        INNER JOIN movie_category_mapping mcm ON m.id = mcm.movieId 
        WHERE mcm.categoryId = :categoryId 
        ORDER BY mcm.position ASC 
        LIMIT :limit
    """)
    fun getMoviesForHomeScreen(categoryId:String, limit:Int = 20): Flow<List<MovieEntity>>


    //Write Queries
    @Upsert
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryMapping(mappings: List<MovieCategoryMappingEntity>)


    //Clearing Data
    @Query("DELETE FROM movie_category_mapping WHERE categoryId = :categoryId")
    suspend fun clearMappingsForCategory(categoryId:String)
}
