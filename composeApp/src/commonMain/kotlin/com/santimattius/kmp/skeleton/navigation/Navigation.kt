package com.santimattius.kmp.skeleton.navigation

import androidx.compose.runtime.Composable
import com.santimattius.kmp.skeleton.features.favorites.FavoriteRoute
import com.santimattius.kmp.skeleton.features.home.HomeScreenRoute
import com.santimattius.kmp.skeleton.features.splash.SplashScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun Navigation(
    navController: Navigator = rememberNavigator(),
) {
    NavHost(
        navigator = navController,
        initialRoute = Features.Splash.route
    ) {
        scene(
            route = Features.Splash.route
        ) {
            SplashScreen {
                with(navController) {
                    popBackStack()
                    navigate(Features.Home.route)
                }
            }
        }
        scene(
            route = Features.Home.route,
        ) {
            HomeScreenRoute()
        }

        scene(
            route = Features.Favorites.route
        ) {
            FavoriteRoute()
        }
    }
}
