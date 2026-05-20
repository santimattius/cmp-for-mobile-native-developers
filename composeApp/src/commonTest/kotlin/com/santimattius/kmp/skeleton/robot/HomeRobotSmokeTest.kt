package com.santimattius.kmp.skeleton.robot

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.v2.runComposeUiTest
import com.santimattius.kmp.skeleton.features.home.HomeScreen
import com.santimattius.kmp.skeleton.features.home.HomeUiState
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class HomeRobotSmokeTest {

    @Test
    fun homeRobotAssertLoadingDetectsLoadingState() = runComposeUiTest {
        setContent {
            HomeScreen(
                state = HomeUiState(isLoading = true),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        HomeRobot(this).assertLoading()
    }

    @Test
    fun homeRobotAssertErrorDetectsErrorState() = runComposeUiTest {
        setContent {
            HomeScreen(
                state = HomeUiState(error = "Something went wrong"),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        HomeRobot(this).assertError()
    }

    @Test
    fun homeRobotAssertEmptyDetectsEmptyState() = runComposeUiTest {
        setContent {
            HomeScreen(
                state = HomeUiState(characters = emptyList()),
                onRefresh = {},
                onCharacterClick = {},
                onFavoriteClick = {},
            )
        }
        HomeRobot(this).assertEmpty()
    }
}
