package com.santimattius.kmp.skeleton

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.santimattius.kmp.skeleton.navigation.Features
import com.santimattius.kmp.skeleton.navigation.NavItem
import com.santimattius.kmp.skeleton.navigation.navigatePoppingUpToStartDestination
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState = remember(scaffoldState, navController, coroutineScope) {
    AppState(scaffoldState, navController, coroutineScope)
}

class AppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    private val coroutineScope: CoroutineScope,
) {

    companion object {
        val HOME_ROUTES = listOf(NavItem.Home.navCommand.route)
    }

    private val currentRoute: String
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route.orEmpty()


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

    fun onNavItemClick(navItem: NavItem) {
        navController.navigatePoppingUpToStartDestination(navItem.navCommand.route)
    }
}