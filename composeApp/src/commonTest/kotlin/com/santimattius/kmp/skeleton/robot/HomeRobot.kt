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
class HomeRobot(private val composeTest: ComposeUiTest) {

    // finders
    private fun grid() = composeTest.onNodeWithTag(TestTags.HomeGrid)
    private fun item(id: String) = composeTest.onNodeWithTag(TestTags.homeItem(id))
    private fun favToggle(id: String) = composeTest.onNodeWithTag(TestTags.homeFavoriteToggle(id))

    // actions
    fun waitForContent() = apply {
        composeTest.waitUntilExactlyOneExists(hasTestTag(TestTags.HomeGrid))
    }

    fun openRepository() = apply {
        composeTest.waitUntilExactlyOneExists(hasTestTag(TestTags.HomeOpenRepository))
        composeTest.onNodeWithTag(TestTags.HomeOpenRepository).performClick()
    }

    fun clickItem(id: String) = apply {
        item(id).performScrollTo()
        item(id).performClick()
    }

    fun toggleFavorite(id: String) = apply {
        favToggle(id).performClick()
    }

    // assertions
    fun assertGridVisible() = apply { grid().assertIsDisplayed() }

    fun assertLoading() = apply {
        composeTest.onNodeWithTag(TestTags.HomeLoading).assertIsDisplayed()
    }

    fun assertEmpty() = apply {
        composeTest.onNodeWithTag(TestTags.HomeEmpty).assertIsDisplayed()
    }

    fun assertError() = apply {
        composeTest.onNodeWithTag(TestTags.HomeError).assertIsDisplayed()
    }

    fun assertItemVisible(id: String) = apply {
        item(id).assertIsDisplayed()
    }
}
