package com.vishal.home.tv_shows

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.vishal.domain.shows.model.TVShow
import com.vishal.home.discovery.components.MovieItem
import com.vishal.home.tv_shows.components.TvShowItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowListingScreen(
    viewModel: TvShowsListingViewModel = hiltViewModel()
) {

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        val shows = viewModel.pagedTvShow.collectAsLazyPagingItems()
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
                val selectedCat by viewModel.selectedCategory.collectAsState()
                OutlinedTextField(
                    value = selectedCat,
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
                    ShowCategories.entries.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.name) },
                            onClick = {
                                viewModel.onCategorySelected(category.value)
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
                count = shows.itemCount,
                key = shows.itemKey { it.id }
            ) { index ->
                val tvShow = shows[index]
                tvShow?.let {
                    ShowListItem(item = it)
                }
            }
        }
    }
}

@Composable
fun ShowListItem(item: TVShow) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TvShowItem(
            tvShow = item,
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
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2
            )
            Text(
                text = "Rating: ${item.rating}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Release: ${item.firstAirDate}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


enum class ShowCategories(val value: String) {
    Popular("popular"),
    TopRated("top_rated"),
    Upcoming("upcoming"),
}