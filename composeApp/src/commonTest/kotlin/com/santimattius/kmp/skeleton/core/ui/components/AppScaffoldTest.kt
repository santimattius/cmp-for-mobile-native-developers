package com.santimattius.kmp.skeleton.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class AppScaffoldTest {

    @Test
    fun appScaffoldRendersTopBarContent() = runComposeUiTest {
        setContent {
            AppScaffold(topBar = { Text("T") }) {}
        }
        onNodeWithText("T").assertIsDisplayed()
    }

    @Test
    fun appScaffoldRendersSnackbarHostContent() = runComposeUiTest {
        setContent {
            AppScaffold(snackbarHost = { Text("snackbar") }) {}
        }
        onNodeWithText("snackbar").assertIsDisplayed()
    }
}
