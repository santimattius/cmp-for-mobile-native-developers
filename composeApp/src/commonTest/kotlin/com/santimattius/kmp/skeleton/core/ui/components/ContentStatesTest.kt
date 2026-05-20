package com.santimattius.kmp.skeleton.core.ui.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ContentStatesTest {

    @Test
    fun loadingContentRenders() = runComposeUiTest {
        setContent {
            LoadingContent()
        }
        onNodeWithContentDescription("Loading").assertIsDisplayed()
    }

    @Test
    fun emptyContentRendersMessage() = runComposeUiTest {
        setContent {
            EmptyContent(message = "Nothing here")
        }
        onNodeWithText("Nothing here").assertIsDisplayed()
    }

    @Test
    fun errorContentRendersRetryButton() = runComposeUiTest {
        setContent {
            ErrorContent(message = "Something went wrong", onRetry = {})
        }
        onNodeWithText("Something went wrong").assertIsDisplayed()
        onNodeWithContentDescription("Retry").assertIsDisplayed()
        onNodeWithContentDescription("Retry").assertHasClickAction()
    }
}
