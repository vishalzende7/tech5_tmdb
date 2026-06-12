package com.vishal.home.movie_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.vishal.home.discovery.MovieListState
import com.vishal.home.discovery.components.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListContent(
    state: MovieListState,
    onCategorySelected: (String) -> Unit
) {
    val movies = state.movies.collectAsLazyPagingItems()
    var expanded by remember { mutableStateOf(false) }
    val categories = listOf("Popular", "Top_Rated", "Upcoming")

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = state.selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                onCategorySelected(category)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = movies.itemCount,
                key = movies.itemKey { it.id }
            ) { index ->
                val movie = movies[index]
                movie?.let {
                    MovieListItem(movie = it)
                }
            }
        }
    }
}

@Composable
fun MovieListItem(movie: com.vishal.domain.movies.model.Movie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MovieItem(
            movie = movie,
            onClick = {},
            modifier = Modifier.width(120.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
            Text(
                text = "Rating: ${movie.rating}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Release: ${movie.releaseDate}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
