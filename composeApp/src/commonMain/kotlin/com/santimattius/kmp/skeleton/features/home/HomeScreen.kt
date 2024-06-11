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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cmp_for_mobile_native_developers.composeapp.generated.resources.Res
import cmp_for_mobile_native_developers.composeapp.generated.resources.app_name
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.ui.components.AppBar
import com.santimattius.kmp.skeleton.core.ui.components.AppBarIcon
import com.santimattius.kmp.skeleton.core.ui.components.AppBarIconModel
import com.santimattius.kmp.skeleton.core.ui.components.NetworkImage
import org.jetbrains.compose.resources.stringResource

object HomeScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = getScreenModel<HomeScreenModel>()
        HomeScreenContent(screenModel)
    }
}

@Composable
fun HomeScreenContent(
    screenModel: HomeScreenModel,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {},
    goToFavorite: () -> Unit = {}
) {
    val state by screenModel.state.collectAsState()

    Scaffold(
        topBar = {
            AppBar(
                title = stringResource(Res.string.app_name),
                actions = {
                    AppBarIcon(
                        navIcon = AppBarIconModel(
                            icon = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            action = goToFavorite
                        )
                    )
                }
            )
        }
    ) { padding ->
        GridOfCharacters(
            characters = state.data,
            padding = padding,
            onClick = onClick,
            onFavorite = onFavorite
        )
    }
}

@Composable
private fun GridOfCharacters(
    characters: List<Character>,
    padding: PaddingValues,
    onClick: (Character) -> Unit = {},
    onFavorite: (Character) -> Unit = {},
) {

    LazyVerticalGrid(
        columns = GridCells.Adaptive(2.dp),
        contentPadding = PaddingValues(2.dp),
        modifier = Modifier.padding(padding)
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
