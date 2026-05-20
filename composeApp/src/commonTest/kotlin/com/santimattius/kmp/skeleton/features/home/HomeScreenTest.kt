package com.santimattius.kmp.skeleton.features.home

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.robot.HomeRobot
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class HomeScreenTest {

    @Test
    fun homeScreenShowsLoadingState() = runComposeUiTest {
        setContent {
            HomeScreen(
                state = HomeUiState(isLoading = true),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        HomeRobot(this).assertLoading()
    }

    @Test
    fun homeScreenShowsEmptyState() = runComposeUiTest {
        setContent {
            HomeScreen(
                state = HomeUiState(characters = emptyList()),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        HomeRobot(this).assertEmpty()
    }

    @Test
    fun homeScreenShowsErrorState() = runComposeUiTest {
        setContent {
            HomeScreen(
                state = HomeUiState(error = "Network error"),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        HomeRobot(this).assertError()
    }

    @Test
    fun homeScreenShowsCharacterGrid() = runComposeUiTest {
        val character = Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = false)
        setContent {
            HomeScreen(
                state = HomeUiState(characters = listOf(character)),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        HomeRobot(this)
            .assertGridVisible()
            .assertItemVisible(character.id.toString())
    }

    @Test
    fun homeScreenFavoriteToggleIsClickable() = runComposeUiTest {
        val character = Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = false)
        var favoriteClicked = false
        setContent {
            HomeScreen(
                state = HomeUiState(characters = listOf(character)),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = { favoriteClicked = true },
            )
        }
        HomeRobot(this).toggleFavorite(character.id.toString())
        assertTrue(favoriteClicked, "onFavoriteClick lambda was not invoked")
    }
}
