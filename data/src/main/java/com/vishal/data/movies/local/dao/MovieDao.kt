package com.vishal.data.movies.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.vishal.data.movies.local.entity.MovieCategoryMappingEntity
import com.vishal.data.movies.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query(
        """
        SELECT m.* FROM movies m 
        INNER JOIN movie_category_mapping mcm ON m.id = mcm.movieId 
        WHERE mcm.categoryId = :categoryId 
        ORDER BY mcm.position ASC 
        LIMIT :limit
    """
    )
    fun getMoviesForHomeScreen(categoryId: String, limit: Int = 20): Flow<List<MovieEntity>>

    @Query(
        """
    SELECT m.* FROM movies m 
    INNER JOIN movie_category_mapping mcm ON m.id = mcm.movieId 
    WHERE mcm.categoryId = :categoryId 
    ORDER BY mcm.position ASC
    """
    )
    fun getMoviesForPaging(categoryId: String): PagingSource<Int, MovieEntity>

    @Query("SELECT * from movies where id = :movieId LIMIT 1")
    fun getMovie(movieId:Int): MovieEntity?

    @Upsert
    suspend fun insertMovie(entity: MovieEntity)

    //Write Queries
    @Upsert
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryMapping(mappings: List<MovieCategoryMappingEntity>)


    //Clearing Data
    @Query("DELETE FROM movie_category_mapping WHERE categoryId = :categoryId")
    suspend fun clearMappingsForCategory(categoryId: String)

}
