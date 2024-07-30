package com.santimattius.kmp.skeleton

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.santimattius.kmp.skeleton.navigation.Features
import kotlinx.coroutines.CoroutineScope
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: Navigator = rememberNavigator(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState = remember(scaffoldState, navController, coroutineScope) {
    AppState(scaffoldState, navController, coroutineScope)
}

class AppState(
    val scaffoldState: ScaffoldState,
    val navController: Navigator,
    private val coroutineScope: CoroutineScope,
) {

    companion object {
        val HOME_ROUTES = listOf(Features.Home.route)
    }

    private val currentRoute: String
        @Composable get() = navController.currentEntry.collectAsState(null).value?.route?.route.orEmpty()


    val showUpNavigation: Boolean
        @Composable get() = !HOME_ROUTES.contains(currentRoute)

    val showTopAppBar: Boolean
        @Composable get() = currentRoute.notContainsRoute(Features.Splash)


    fun onUpClick() {
        navController.popBackStack()
    }

    private fun String.notContainsRoute(feature: Features): Boolean {
        return if (isBlank()) {
            false
        } else {
            !contains(feature.route)
        }
    }

    fun onNavItemClick(feature: Features) {
        navController.navigate(feature.route)
    }
}