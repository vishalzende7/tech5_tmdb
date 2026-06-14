package com.vishal.tmdb

import android.content.Context
import androidx.room.Room
import com.vishal.core.network.AuthInterceptor
import com.vishal.core.network.RetryInterceptor
import com.vishal.data.database.AppDatabase
import com.vishal.data.movies.local.dao.MovieDao
import com.vishal.data.movies.local.dao.MovieRemoteKeysDao
import com.vishal.data.shows.local.dao.TVShowDao
import com.vishal.data.remote.TmdbApiService
import com.vishal.data.movies.repository.MoviesRepositoryImpl
import com.vishal.data.people.repository.PeopleRepositoryImpl
import com.vishal.data.shows.repository.TVShowsRepositoryImpl
import com.vishal.domain.movies.repository.MoviesRepository
import com.vishal.domain.people.repository.PeopleRepository
import com.vishal.domain.shows.repository.TVShowsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.nerdythings.okhttp.profiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
abstract class SingletonModule {

    @Binds
    @Singleton
    abstract fun provideMovieRepository(repo: MoviesRepositoryImpl): MoviesRepository

    @Binds
    @Singleton
    abstract fun provideShowsRepository(repo: TVShowsRepositoryImpl): TVShowsRepository

    @Binds
    @Singleton
    abstract fun providePeopleRepository(repository: PeopleRepositoryImpl): PeopleRepository

}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(BuildConfig.TOKEN))
            .addInterceptor(RetryInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        // Here is where the Base URL is added!
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkMovieSource(retrofit: Retrofit): TmdbApiService {
        return retrofit.create(TmdbApiService::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTmdbDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "offline_db"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: AppDatabase): MovieDao {
        return database.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieRemoteKeysDao(database: AppDatabase): MovieRemoteKeysDao {
        return database.movieRemoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideTVShowDao(database: AppDatabase): TVShowDao {
        return database.tvShowDao()
    }
}