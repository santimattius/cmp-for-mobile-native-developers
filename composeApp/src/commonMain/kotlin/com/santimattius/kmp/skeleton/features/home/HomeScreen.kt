package com.santimattius.kmp.skeleton.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.ui.components.Center
import com.santimattius.kmp.skeleton.core.ui.components.LoadingIndicator
import com.santimattius.kmp.skeleton.core.ui.components.NetworkImage
import moe.tlaster.precompose.koin.koinViewModel

@Composable
fun HomeScreenRoute() {
    val viewModel = koinViewModel(HomeViewModel::class)
    HomeScreenContent(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
        viewModel = viewModel,
        onClick = {},
        onFavorite = viewModel::addToFavorites,
    )
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    when {
        state.isLoading -> {
            LoadingIndicator()
        }

        state.data.isEmpty() -> {
            Center {
                Text(
                    text = "There is no favorite content",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }

        else -> {
            GridOfCharacters(
                modifier = modifier,
                characters = state.data,
                onClick = onClick,
                onFavorite = onFavorite
            )
        }
    }
}

@Composable
private fun GridOfCharacters(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {},
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(2.dp),
        modifier = modifier
    ) {

        items(characters, key = { it.id }) { character ->
            CharacterItem(
                character = character,
                modifier = Modifier.clickable { onClick(character) },
                onClick = onClick,
                onFavorite = onFavorite
            )
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(2.dp)
            .clickable { onClick(character) }) {
        Box(contentAlignment = Alignment.BottomEnd) {
            NetworkImage(
                imageUrl = character.image,
                contentDescription = character.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .aspectRatio(ratio = 0.67f),
            )
            IconButton(onClick = { onFavorite(character) }) {
                Icon(
                    imageVector = if (character.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorite",
                    tint = if (character.isFavorite) Color.Red else Color.White
                )
            }
        }
    }
}
