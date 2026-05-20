package com.santimattius.kmp.skeleton.features.repository

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.v2.runComposeUiTest
import com.santimattius.kmp.skeleton.di.KoinTestContext
import com.santimattius.kmp.skeleton.navigation.Navigation
import com.santimattius.kmp.skeleton.robot.HomeRobot
import com.santimattius.kmp.skeleton.robot.RepositoryRobot
import kotlin.test.Test
import org.koin.compose.KoinContext

@OptIn(ExperimentalTestApi::class)
class HomeToRepositoryTest {

    @Test
    fun openRepositoryFromHomeShowsRepositoryScreen() = runComposeUiTest {
        setContent {
            KoinContext(KoinTestContext.koin) {
                Navigation()
            }
        }
        HomeRobot(this).waitForContent().openRepository()
        RepositoryRobot(this).waitForScreen().assertScreenDisplayed()
    }

    @Test
    fun backFromRepositoryReturnsToHome() = runComposeUiTest {
        setContent {
            KoinContext(KoinTestContext.koin) {
                Navigation()
            }
        }
        HomeRobot(this).waitForContent().openRepository()
        RepositoryRobot(this).waitForScreen()
        onNodeWithContentDescription("Back").performClick()
        HomeRobot(this).waitForContent().assertGridVisible()
    }
}
