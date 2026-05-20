package com.santimattius.kmp.skeleton.features.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import cmp_for_mobile_native_developers.composeapp.generated.resources.Res
import cmp_for_mobile_native_developers.composeapp.generated.resources.favorite_icon_description
import cmp_for_mobile_native_developers.composeapp.generated.resources.favorites
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.ui.components.AppScaffold
import com.santimattius.kmp.skeleton.core.ui.components.EmptyContent
import com.santimattius.kmp.skeleton.core.ui.components.LoadingContent
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags
import com.santimattius.kmp.skeleton.core.ui.themes.AppSpacing
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoriteRoute(
    onNavigateToDetail: (String) -> Unit = {},
    onBack: () -> Unit = {},
) {
    val viewModel = koinViewModel<FavoritesViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    FavoriteScreen(
        state = state,
        onClick = { character -> onNavigateToDetail(character.id.toString()) },
        onFavoriteClick = viewModel::toggleFavorite,
        onBack = onBack,
    )
}

@Composable
fun FavoriteScreen(
    state: FavoritesUiState,
    onClick: (Character) -> Unit = {},
    onFavoriteClick: (Character) -> Unit = {},
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        modifier = modifier.testTag(TestTags.FavoritesScreen),
        topBar = { FavoritesTopAppBar(onBack) },
    ) { paddingValues ->
        when {
            state.isLoading -> LoadingContent(modifier = Modifier.padding(paddingValues))

            state.characters.isEmpty() -> EmptyContent(
                message = "There is no favorite content",
                modifier = Modifier
                    .padding(paddingValues)
                    .testTag(TestTags.FavoritesEmpty)
                    .background(MaterialTheme.colorScheme.background),
            )

            else -> ListOfFavorites(
                modifier = Modifier
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                characters = state.characters,
                onClick = onClick,
                onFavoriteClick = onFavoriteClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesTopAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(Res.string.favorites)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
    )
}

@Composable
private fun ListOfFavorites(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    onClick: (Character) -> Unit = {},
    onFavoriteClick: (Character) -> Unit = {},
) {
    LazyColumn(
        contentPadding = PaddingValues(AppSpacing.xs / 2),
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.FavoritesList)
            .padding(horizontal = AppSpacing.sm, vertical = AppSpacing.md),
    ) {
        items(characters, key = { it.id }) { character ->
            FavoriteRowItem(
                onClick = onClick,
                character = character,
                onFavoriteClick = onFavoriteClick,
                itemModifier = Modifier.testTag(TestTags.favoriteItem(character.id.toString())),
                toggleModifier = Modifier.testTag(TestTags.favoriteToggle(character.id.toString())),
            )
        }
    }
}

@Composable
private fun FavoriteRowItem(
    onClick: (Character) -> Unit,
    character: Character,
    onFavoriteClick: (Character) -> Unit,
    itemModifier: Modifier = Modifier,
    toggleModifier: Modifier = Modifier,
) {
    ListItem(
        modifier = itemModifier.clickable { onClick(character) },
        leadingContent = {
            CircularAvatar(
                image = character.image,
                contentDescription = character.name,
                size = AppSpacing.xl + AppSpacing.lg,
            )
        },
        headlineContent = { Text(text = character.name) },
        trailingContent = {
            IconButton(
                onClick = { onFavoriteClick(character) },
                modifier = toggleModifier,
            ) {
                Icon(
                    imageVector = if (character.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(Res.string.favorite_icon_description),
                    tint = Color.Red,
                )
            }
        },
    )
}

@Composable
private fun CircularAvatar(
    image: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    size: Dp = AppSpacing.xl,
) {
    Box(
        modifier = modifier
            .size(size)
            .background(color = MaterialTheme.colorScheme.surface, shape = CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        AsyncImage(
            model = image,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(size),
        )
    }
}

