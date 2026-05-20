package com.santimattius.kmp.skeleton.features.detail

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.v2.runComposeUiTest
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.robot.DetailRobot
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class CharacterDetailScreenTest {

    @Test
    fun detailScreenShowsLoadingState() = runComposeUiTest {
        setContent {
            CharacterDetailScreen(
                state = CharacterDetailUiState(isLoading = true),
                onRetry = {},
                onBack = {},
            )
        }
        DetailRobot(this).assertLoading()
    }

    @Test
    fun detailScreenShowsErrorState() = runComposeUiTest {
        setContent {
            CharacterDetailScreen(
                state = CharacterDetailUiState(isLoading = false, error = "Not found"),
                onRetry = {},
                onBack = {},
            )
        }
        DetailRobot(this)
            .assertError()
            .assertBackButtonVisible()
        onNodeWithContentDescription("Retry").assertHasClickAction()
    }

    @Test
    fun detailScreenShowsCharacterName() = runComposeUiTest {
        val character = Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = false)
        setContent {
            CharacterDetailScreen(
                state = CharacterDetailUiState(isLoading = false, character = character),
                onRetry = {},
                onBack = {},
            )
        }
        onAllNodesWithText("Rick Sanchez")[0].assertIsDisplayed()
    }

    @Test
    fun detailScreenBackButtonIsClickable() = runComposeUiTest {
        var backClicked = false
        setContent {
            CharacterDetailScreen(
                state = CharacterDetailUiState(isLoading = true),
                onRetry = {},
                onBack = { backClicked = true },
            )
        }
        DetailRobot(this).clickBack()
        assertTrue(backClicked, "onBack lambda was not invoked")
    }

    @Test
    fun detailScreenShowsNativeContentForCharacter() = runComposeUiTest {
        val character = Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = false)
        setContent {
            CharacterDetailScreen(
                state = CharacterDetailUiState(isLoading = false, character = character),
                onRetry = {},
                onBack = {},
            )
        }
        DetailRobot(this).assertScreenVisible()
        onAllNodesWithText("Rick Sanchez")[0].assertIsDisplayed()
    }
}
