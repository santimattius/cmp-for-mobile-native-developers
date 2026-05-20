package com.santimattius.kmp.skeleton.features.home

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import androidx.window.core.layout.WindowSizeClass
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags
import com.santimattius.kmp.skeleton.features.detail.CharacterDetailScreen
import com.santimattius.kmp.skeleton.features.detail.CharacterDetailUiState
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalTestApi::class, ExperimentalMaterial3AdaptiveApi::class)
class HomeAdaptiveTest {

    @Test
    fun gridColumnsForCompactIs2() {
        val compact = WindowSizeClass(minWidthDp = 0, minHeightDp = 0)
        assertEquals(2, gridColumnsForWindowSizeClass(compact))
    }

    @Test
    fun gridColumnsForMediumIs3() {
        val medium = WindowSizeClass(minWidthDp = WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND, minHeightDp = 0)
        assertEquals(3, gridColumnsForWindowSizeClass(medium))
    }

    @Test
    fun gridColumnsForExpandedIs4() {
        val expanded = WindowSizeClass(minWidthDp = WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND, minHeightDp = 0)
        assertEquals(4, gridColumnsForWindowSizeClass(expanded))
    }

    @Test
    fun homeListDetailLayoutRendersListPane() = runComposeUiTest {
        val character = Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = false)
        setContent {
            val navigator = rememberListDetailPaneScaffoldNavigator<String>()
            HomeListDetailLayout(
                state = HomeUiState(characters = listOf(character)),
                onCharacterClick = {},
                onFavoriteClick = {},
                onRefresh = {},
                navigator = navigator,
                detailContent = {},
            )
        }
        onNodeWithTag(TestTags.HomeGrid).assertIsDisplayed()
    }

    @Test
    fun homeListDetailLayoutRendersWithEmptyState() = runComposeUiTest {
        setContent {
            val navigator = rememberListDetailPaneScaffoldNavigator<String>()
            HomeListDetailLayout(
                state = HomeUiState(characters = emptyList()),
                onCharacterClick = {},
                onFavoriteClick = {},
                onRefresh = {},
                navigator = navigator,
                detailContent = {},
            )
        }
        onNodeWithTag(TestTags.HomeEmpty).assertIsDisplayed()
    }

    @Test
    fun detailContentShowsCharacterWhenLoaded() = runComposeUiTest {
        val character = Character(id = 1L, name = "Rick Sanchez", image = "", isFavorite = false)
        setContent {
            CharacterDetailScreen(
                state = CharacterDetailUiState(isLoading = false, character = character),
                onRetry = {},
                onBack = {},
            )
        }
        onNodeWithTag(TestTags.DetailScreen).assertIsDisplayed()
    }
}
