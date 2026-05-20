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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowSizeClass
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.ui.components.AppScaffold
import com.santimattius.kmp.skeleton.core.ui.components.EmptyContent
import com.santimattius.kmp.skeleton.core.ui.components.ErrorContent
import com.santimattius.kmp.skeleton.core.ui.components.LoadingContent
import com.santimattius.kmp.skeleton.core.ui.components.NetworkImage
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags
import com.santimattius.kmp.skeleton.core.ui.themes.AppSpacing
import cmp_for_mobile_native_developers.composeapp.generated.resources.Res
import cmp_for_mobile_native_developers.composeapp.generated.resources.app_name
import cmp_for_mobile_native_developers.composeapp.generated.resources.favorite_icon_description
import cmp_for_mobile_native_developers.composeapp.generated.resources.home_open_repository_content_description
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun HomeScreenRoute(
    onNavigateToDetail: (String) -> Unit = {},
    onOpenFavorites: () -> Unit = {},
    onOpenRepository: () -> Unit = {},
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onRefresh = viewModel::refresh,
        onCharacterClick = { character -> onNavigateToDetail(character.id.toString()) },
        onFavoriteClick = viewModel::addToFavorite,
        onOpenFavorites = onOpenFavorites,
        onOpenRepository = onOpenRepository,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeUiState,
    onRefresh: () -> Unit = {},
    onCharacterClick: (Character) -> Unit = {},
    onFavoriteClick: (Character) -> Unit = {},
    onOpenFavorites: () -> Unit = {},
    onOpenRepository: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        modifier = modifier.testTag(TestTags.HomeScreen),
        topBar = { HomeTopAppBar(onOpenFavorites = onOpenFavorites, onOpenRepository = onOpenRepository) },
    ) { paddingValues ->
        when {
            state.isLoading -> LoadingContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .testTag(TestTags.HomeLoading),
            )

            state.error != null -> ErrorContent(
                message = state.error,
                onRetry = onRefresh,
                modifier = Modifier
                    .padding(paddingValues)
                    .testTag(TestTags.HomeError),
            )

            state.characters.isEmpty() -> EmptyContent(
                message = "There is no content",
                modifier = Modifier
                    .padding(paddingValues)
                    .testTag(TestTags.HomeEmpty),
            )

            else -> GridOfCharacters(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = AppSpacing.sm, vertical = AppSpacing.md),
                characters = state.characters,
                onClick = onCharacterClick,
                onFavorite = onFavoriteClick,
            )
        }
    }
}

fun gridColumnsForWindowSizeClass(windowSizeClass: WindowSizeClass): Int = when {
    windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) -> 4
    windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) -> 3
    else -> 2
}

@Composable
private fun GridOfCharacters(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {},
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val columns = gridColumnsForWindowSizeClass(windowSizeClass)
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(AppSpacing.xs / 2),
        modifier = modifier.testTag(TestTags.HomeGrid),
    ) {
        items(characters, key = { it.id }) { character ->
            CharacterItem(
                character = character,
                modifier = Modifier
                    .testTag(TestTags.homeItem(character.id.toString()))
                    .clickable { onClick(character) },
                onClick = onClick,
                onFavorite = onFavorite,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopAppBar(
    onOpenFavorites: () -> Unit,
    onOpenRepository: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = stringResource(Res.string.app_name)) },
        actions = {
            IconButton(
                onClick = onOpenRepository,
                modifier = Modifier.testTag(TestTags.HomeOpenRepository),
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = stringResource(Res.string.home_open_repository_content_description),
                )
            }
            IconButton(onClick = onOpenFavorites) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Open favorites",
                )
            }
        },
    )
}

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(AppSpacing.xs / 2)
            .clickable { onClick(character) },
    ) {
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
            IconButton(
                onClick = { onFavorite(character) },
                modifier = Modifier.testTag(TestTags.homeFavoriteToggle(character.id.toString())),
            ) {
                Icon(
                    imageVector = if (character.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(Res.string.favorite_icon_description),
                    tint = if (character.isFavorite) Color.Red else Color.White,
                )
            }
        }
    }
}

