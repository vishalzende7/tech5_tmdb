package com.vishal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vishal.data.movies.local.dao.MovieDao
import com.vishal.data.movies.local.entity.MovieEntity


@Database(entities = [MovieEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
