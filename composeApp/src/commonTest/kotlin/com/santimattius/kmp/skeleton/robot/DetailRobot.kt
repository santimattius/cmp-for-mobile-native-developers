package com.santimattius.kmp.skeleton.robot

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.waitUntilExactlyOneExists
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags

@OptIn(ExperimentalTestApi::class)
class DetailRobot(private val composeTest: ComposeUiTest) {

    // finders
    private fun backButton() = composeTest.onNodeWithTag(TestTags.DetailBack)

    // actions
    fun waitForContent() = apply {
        composeTest.waitUntilExactlyOneExists(hasTestTag(TestTags.DetailScreen))
    }

    fun clickBack() = apply {
        backButton().performClick()
    }

    // assertions
    fun assertScreenVisible() = apply {
        composeTest.onNodeWithTag(TestTags.DetailScreen).assertIsDisplayed()
    }

    fun assertLoading() = apply {
        composeTest.onNodeWithTag(TestTags.DetailLoading).assertIsDisplayed()
    }

    fun assertError() = apply {
        composeTest.onNodeWithTag(TestTags.DetailError).assertIsDisplayed()
    }

    fun assertBackButtonVisible() = apply {
        backButton().assertIsDisplayed()
        backButton().assertHasClickAction()
    }
}
