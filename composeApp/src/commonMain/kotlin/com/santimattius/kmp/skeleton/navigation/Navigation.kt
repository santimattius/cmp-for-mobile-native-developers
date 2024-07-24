package com.santimattius.kmp.skeleton.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.santimattius.kmp.skeleton.features.favorites.FavoriteRoute
import com.santimattius.kmp.skeleton.features.home.HomeScreenRoute
import com.santimattius.kmp.skeleton.features.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Features.Splash.route
    ) {
        composable(
            features = Features.Splash
        ) {
            SplashScreen {
                with(navController) {
                    popBackStack()
                    navigate(Features.Home)
                }
            }
        }
        composable(
            features = Features.Home,
        ) {
            HomeScreenRoute()
        }

        composable(
            features = Features.Favorites
        ) {
            FavoriteRoute()
        }
    }
}

private fun NavController.navigate(route: Features) {
    navigate(route.route)
}
