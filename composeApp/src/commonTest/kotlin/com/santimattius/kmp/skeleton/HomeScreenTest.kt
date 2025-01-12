package com.santimattius.kmp.skeleton

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.runComposeUiTest
import androidx.compose.ui.test.waitUntilExactlyOneExists
import com.santimattius.kmp.skeleton.di.KoinTestContext
import com.santimattius.kmp.skeleton.features.home.HomeScreenRoute
import org.koin.compose.KoinContext
import org.koin.test.KoinTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class HomeScreenTest : KoinTest {

    @Test
    fun firstTestHomeScreen() = runComposeUiTest {
        setContent {
            KoinContext(KoinTestContext.koin) {
                HomeScreenRoute()
            }
        }
        waitUntilExactlyOneExists(
            matcher = hasContentDescriptionExactly("Rick Sanchez"),
            timeoutMillis = 5000
        )
        onNodeWithContentDescription("Rick Sanchez").assertIsDisplayed()
    }
}