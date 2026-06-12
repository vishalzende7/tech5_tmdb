# TMDB Project Structure

This document outlines the directory and module structure of the TMDB Android project.

## Root Directory
- `app/`: Main application module.
- `core/`: Shared core modules.
- `data/`: Data layer module.
- `domain/`: Domain layer module.
- `features/`: Feature-specific modules.
- `gradle/`: Gradle wrapper and version catalogs.
- `build.gradle.kts`: Root build script.
- `settings.gradle.kts`: Project settings and module definitions.

---

## App Module (`app`)
The `app` module serves as the entry point of the application.
- `src/main/java/com/vishal/tmdb/`
    - `Application.kt`: Custom Application class.
    - `MainActivity.kt`: Main Activity.
    - `SingletonModule.kt`: Hilt dependency injection module for singleton components.
    - `navigation/`: Navigation logic (e.g., `AppNavHost.kt`).
    - `ui/theme/`: Compose theme definitions (`Color.kt`, `Theme.kt`, `Type.kt`).

---

## Core Modules (`core`)
Contains shared infrastructure and utilities.
- `database/`: Database configuration and entities (Room).
- `network/`: Network configuration (Retrofit, OkHttp).
    - `src/main/java/com/vishal/core/network/`
        - `AuthInterceptor.kt`: Interceptor for API authentication.

---

## Data Module (`data`)
Implements the data layer, handling local and remote data sources.
- `src/main/java/com/vishal/data/`
    - `movies/`
        - `local/entity/`: Room entities (e.g., `MovieEntity.kt`).
        - `mapper/`: Data mappers (e.g., `MovieMapper.kt`).
        - `remote/`: Retrofit API services and DTOs.
            - `TmdbApiService.kt`
            - `dto/`: Data Transfer Objects (e.g., `MovieDto.kt`, `MovieResponseDto.kt`).
        - `repository/`: Repository implementations (e.g., `MoviesRepositoryImpl.kt`).
    - `people/repository/`: Repository implementation for people.
    - `shows/repository/`: Repository implementation for TV shows.

---

## Domain Module (`domain`)
Contains business logic, models, and repository interfaces.
- `src/main/java/com/vishal/domain/`
    - `core/`: Core domain utilities (e.g., `Resource.kt`).
    - `movies/`
        - `model/`: Domain models (e.g., `Movie.kt`, `MovieDetails.kt`).
        - `repository/`: Repository interfaces.
        - `usecase/`: Business use cases (e.g., `GetPopularMoviesUseCase.kt`).
    - `people/`
        - `model/`: Person domain models.
        - `repository/`: People repository interface.
    - `shows/`
        - `model/`: TV show domain models.
        - `repository/`: TV shows repository interface.

---

## Feature Modules (`features`)
UI and logic for specific features.
- `home/`: Home screen and discovery logic.
    - `src/main/java/com/vishal/home/discovery/`
        - `HomeScreen.kt`: Home screen UI.
        - `HomeState.kt`: Home screen state.
        - `HomeViewModel.kt`: ViewModel for the home screen.
        - `components/`: UI components (e.g., `MovieCarousel.kt`, `TVShowCarousel.kt`).
- `movies/`: Movie list and related screens.
- `details/`: Detailed view for movies, shows, and people.
- `peoples/`: People-related screens.
- `tvshows/`: TV show-related screens.
