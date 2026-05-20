package com.santimattius.kmp.skeleton.core.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PlatformWebViewTest {

    @Test
    fun platformWebViewRendersWithoutCrash() = runComposeUiTest {
        setContent {
            PlatformWebViewWrapper(url = "https://rickandmortyapi.com/")
        }
        onNodeWithTag("platform_web_view_wrapper").assertIsDisplayed()
    }
}
