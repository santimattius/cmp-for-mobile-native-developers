package com.santimattius.kmp.skeleton.robot

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.waitUntilExactlyOneExists
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags

@OptIn(ExperimentalTestApi::class)
class FavoritesRobot(private val composeTest: ComposeUiTest) {

    // finders
    private fun list() = composeTest.onNodeWithTag(TestTags.FavoritesList)
    private fun item(id: String) = composeTest.onNodeWithTag(TestTags.favoriteItem(id))
    private fun toggle(id: String) = composeTest.onNodeWithTag(TestTags.favoriteToggle(id))

    // actions
    fun waitForContent() = apply {
        composeTest.waitUntilExactlyOneExists(hasTestTag(TestTags.FavoritesList))
    }

    fun clickItem(id: String) = apply {
        item(id).performScrollTo()
        item(id).performClick()
    }

    fun toggleFavorite(id: String) = apply {
        toggle(id).performClick()
    }

    // assertions
    fun assertListVisible() = apply { list().assertIsDisplayed() }

    fun assertEmpty() = apply {
        composeTest.onNodeWithTag(TestTags.FavoritesEmpty).assertIsDisplayed()
    }

    fun assertItemVisible(id: String) = apply {
        item(id).assertIsDisplayed()
    }
}
