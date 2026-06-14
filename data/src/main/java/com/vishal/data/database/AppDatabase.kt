package com.vishal.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vishal.data.movies.local.dao.MovieDao
import com.vishal.data.movies.local.dao.MovieRemoteKeysDao
import com.vishal.data.movies.local.entity.MovieCategoryMappingEntity
import com.vishal.data.movies.local.entity.MovieEntity
import com.vishal.data.movies.local.entity.MovieRemoteKeys
import com.vishal.data.shows.local.dao.TVShowDao
import com.vishal.data.shows.local.entity.TVShowEntity


@Database(
    entities = [
        MovieEntity::class,
        TVShowEntity::class,
        MovieRemoteKeys::class,
        MovieCategoryMappingEntity::class],
    version = 12
)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TVShowDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeysDao
}
