package com.santimattius.kmp.skeleton.features.favorites

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.test.waitUntilExactlyOneExists
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags
import com.santimattius.kmp.skeleton.robot.FavoritesRobot
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class FavoritesScreenTest {

    @Test
    fun favoritesScreenShowsEmptyState() = runComposeUiTest {
        setContent {
            FavoriteScreen(
                state = FavoritesUiState(characters = emptyList()),
                onClick = {},
                onFavoriteClick = {},
            )
        }
        FavoritesRobot(this).assertEmpty()
    }

    @Test
    fun favoritesScreenShowsCharacterList() = runComposeUiTest {
        val character = Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = true)
        setContent {
            FavoriteScreen(
                state = FavoritesUiState(characters = listOf(character)),
                onClick = {},
                onFavoriteClick = {},
            )
        }
        FavoritesRobot(this)
            .assertListVisible()
            .assertItemVisible(character.id.toString())
    }

    @Test
    fun favoritesScreenToggleIsClickable() = runComposeUiTest {
        val character = Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = true)
        var toggleClicked = false
        setContent {
            FavoriteScreen(
                state = FavoritesUiState(characters = listOf(character)),
                onClick = {},
                onFavoriteClick = { toggleClicked = true },
            )
        }
        FavoritesRobot(this).toggleFavorite(character.id.toString())
        assertTrue(toggleClicked, "onFavoriteClick lambda was not invoked")
    }

    @Test
    fun favoriteIconHasMeaningfulContentDescription() = runComposeUiTest {
        val character = Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = true)
        setContent {
            FavoriteScreen(
                state = FavoritesUiState(characters = listOf(character)),
                onClick = {},
                onFavoriteClick = {},
            )
        }
        waitUntilExactlyOneExists(
            matcher = hasContentDescriptionExactly("Rick Sanchez"),
            timeoutMillis = 5000,
        )
        onAllNodesWithContentDescription("Favorite")[0].assertIsDisplayed()
    }
}
