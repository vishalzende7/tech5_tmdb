# Project Analysis: TMDB Android App

This document provides a comprehensive analysis of the TMDB project structure, its features, and the purpose of each folder.

## 🏗️ High-Level Architecture
The project follows **Clean Architecture** principles and is organized into multiple modules to promote separation of concerns, scalability, and testability.

### Module Overview
| Module | Layer | Purpose |
| :--- | :--- | :--- |
| `app` | Presentation | Application entry point, DI setup, and global navigation. |
| `features:*` | Presentation | Feature-specific UI and ViewModels (e.g., `home`). |
| `domain` | Business | Pure Kotlin module containing Use Cases, Domain Models, and Repository interfaces. |
| `data` | Data | Implementation of repositories, local (Room) and remote (Retrofit) data sources. |
| `core:*` | Infrastructure | Shared utilities like network interceptors and database configurations. |

---

## 📂 Folder-Wise Purpose

### 1. `app/`
The main entry point for the Android application.
- `navigation/`: Defines the `AppNavHost` and routing logic.
- `ui/theme/`: Centralized Jetpack Compose theme (Colors, Typography, Theme).
- `SingletonModule.kt`: Hilt module for providing app-wide singletons (e.g., Retrofit, Database).
- `MainActivity.kt`: The single activity hosting the Compose UI.

### 2. `core/`
Contains shared infrastructure modules.
- `network/`: Handles network-related logic.
    - `AuthInterceptor.kt`: Appends API keys/tokens to requests.
    - `RetryInterceptor.kt`: Handles automatic retries for failed requests.

### 3. `domain/`
The core business logic layer, independent of any frameworks.
- `core/`: Common domain classes like the `Resource` wrapper for state management.
- `movies/`, `shows/`, `people/`: Organized by entity.
    - `model/`: Plain Kotlin data classes used across the app.
    - `repository/`: Interfaces defining the data contracts.
    - `usecase/`: Single-responsibility classes for business actions (e.g., `GetPopularMoviesUseCase`).

### 4. `data/`
Handles how data is fetched and stored.
- `local/`: Root local data management (`AppDatabase.kt`).
- `movies/`, `shows/`, `people/`: Feature-specific data logic.
    - `local/dao/` & `local/entity/`: Room database access and table definitions.
    - `remote/`: Retrofit services (`TmdbApiService.kt`) and DTOs (`MovieDto.kt`).
    - `mapper/`: Extension functions to convert between DTOs, Entities, and Domain models.
    - `repository/`: Concrete implementations of domain repository interfaces.
- `utils/`: Data layer specific utility functions.

### 5. `features/`
UI-centric modules using Jetpack Compose.
- `home/`: The primary dashboard.
    - `discovery/`: Contains `HomeScreen`, `HomeViewModel`, and `HomeState`.
    - `discovery/components/`: Reusable UI components like `MovieCarousel` and `TVShowCarousel`.
- `movies/`, `tvshows/`, `peoples/`, `details/`: Placeholder modules for future screen expansions.

---

## ✨ Features Implemented

### 1. Movie Discovery
- Fetching and displaying **Popular**, **Top Rated**, **Trending**, and **Upcoming** movies.
- Displayed in a carousel format on the Home Screen.

### 2. TV Show Discovery
- Fetching and displaying **Top Rated** TV shows.
- Integrated into the Home Screen dashboard.

### 3. Offline Support (Trending Movies)
- **Single Source of Truth**: Trending movies are cached in a local Room database.
- **Offline Access**: Users can view previously fetched trending movies without an internet connection.
- **Auto-Sync**: The app fetches from the API and updates the local cache seamlessly.

### 4. Robust Networking
- Centralized `AuthInterceptor` for API authentication.
- `RetryInterceptor` to handle flaky network connections.
- Global `Resource` state management (Loading, Success, Error).

---

## 🛠️ Tech Stack
- **UI**: Jetpack Compose
- **Architecture**: Clean Architecture + MVVM
- **Dependency Injection**: Hilt
- **Local Database**: Room
- **Networking**: Retrofit + OkHttp
- **Asynchronous Work**: Kotlin Coroutines & Flow
