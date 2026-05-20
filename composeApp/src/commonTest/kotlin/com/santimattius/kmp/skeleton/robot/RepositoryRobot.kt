package com.santimattius.kmp.skeleton.robot

import androidx.compose.ui.test.ComposeUiTest
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.waitUntilExactlyOneExists
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags

@OptIn(ExperimentalTestApi::class)
class RepositoryRobot(private val composeTest: ComposeUiTest) {

    fun waitForScreen() = apply {
        composeTest.waitUntilExactlyOneExists(hasTestTag(TestTags.RepositoryScreen))
    }

    fun assertScreenDisplayed() = apply {
        composeTest.onNodeWithTag(TestTags.RepositoryScreen).assertIsDisplayed()
    }

    fun assertWebViewPresent() = apply {
        composeTest.onNodeWithTag(TestTags.RepositoryWebView).assertIsDisplayed()
    }
}
