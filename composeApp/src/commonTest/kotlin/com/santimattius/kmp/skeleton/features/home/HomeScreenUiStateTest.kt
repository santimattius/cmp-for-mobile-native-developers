package com.santimattius.kmp.skeleton.features.home

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.compose.ui.test.waitUntilExactlyOneExists
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class HomeScreenUiStateTest {

    @Test
    fun homeScreenShowsLoadingWhenIsLoadingIsTrue() = runComposeUiTest {
        setContent {
            HomeScreen(
                state = HomeUiState(isLoading = true),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        onNodeWithTag(TestTags.HomeLoading).assertIsDisplayed()
    }

    @Test
    fun homeScreenShowsCharacterGridWhenCharactersAreNonEmpty() = runComposeUiTest {
        val characters = listOf(
            Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = false),
            Character(id = 2L, name = "Morty Smith", image = "", isFavorite = true),
        )
        setContent {
            HomeScreen(
                state = HomeUiState(characters = characters),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        waitUntilExactlyOneExists(
            matcher = hasTestTag(TestTags.HomeGrid),
            timeoutMillis = 5000,
        )
        onNodeWithTag(TestTags.HomeGrid).assertIsDisplayed()
        onNodeWithContentDescription("Rick Sanchez").assertIsDisplayed()
        onNodeWithContentDescription("Morty Smith").assertIsDisplayed()
    }

    @Test
    fun homeScreenShowsErrorWhenErrorIsNonNull() = runComposeUiTest {
        setContent {
            HomeScreen(
                state = HomeUiState(error = "Something went wrong"),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        onNodeWithTag(TestTags.HomeError).assertIsDisplayed()
        onNodeWithContentDescription("Retry").assertIsDisplayed()
    }
}
