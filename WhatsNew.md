# What's New - Trending Movies Feature

This document summarizes the changes introduced for the **Trending Movies** feature.

## Features Added
- **Trending Movies Support**: Users can now fetch trending movies from the TMDB API and view them even when offline, thanks to the new local caching mechanism.

## Architectural Changes

### Core Database (`core:database`)
- **Room Integration**: Added Room database to the project.
- **MovieEntity**: A new entity to store movie data locally. It includes an `isTrending` flag to distinguish trending movies from others in the same table.
- **MovieDao**: Provides methods to fetch trending movies, insert movies, and clear trending status.
- **AppDatabase**: The main database entry point.

### Data Layer (`data`)
- **Offline-First Strategy**: Updated `MoviesRepositoryImpl` to fetch data from the API and sync it with the local Room database. The database now serves as the single source of truth.
- **Mappers**: Added `toEntity` and `toDomain` mappers in `MovieMapper` to handle data conversion between DTOs, Entities, and Domain models.

### Domain Layer (`domain`)
- **GetTrendingMoviesUseCase**: A new use case to fetch trending movies, simplifying the interaction for the UI layer.

## How to use
To get trending movies in your ViewModel, inject `GetTrendingMoviesUseCase` and call it:

```kotlin
val result = getTrendingMoviesUseCase("day")
if (result is Resource.Success) {
    val trendingMovies = result.data
}
```

## Verification
- Code structure follows Clean Architecture.
- Local caching implemented using Room.
- API integration updated in the repository.
