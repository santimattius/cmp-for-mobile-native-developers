package com.santimattius.kmp.skeleton.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.santimattius.kmp.skeleton.features.favorites.FavoriteRoute
import com.santimattius.kmp.skeleton.features.home.HomeScreenRoute
import com.santimattius.kmp.skeleton.features.splash.SplashScreen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen {
                with(navController) {
                    popBackStack()
                    navigate(Home)
                }
            }
        }
        composable<Home> {
            HomeScreenRoute()
        }

        composable<Favorites> {
            FavoriteRoute()
        }
    }
}
