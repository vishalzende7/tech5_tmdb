package com.vishal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vishal.data.movies.local.dao.MovieDao
import com.vishal.data.movies.local.entity.MovieEntity
import com.vishal.data.shows.local.dao.TVShowDao
import com.vishal.data.shows.local.entity.TVShowEntity


@Database(entities = [MovieEntity::class, TVShowEntity::class], version = 7)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TVShowDao
}
