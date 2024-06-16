package com.santimattius.kmp.skeleton.features.splash

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.santimattius.kmp.skeleton.features.home.HomeScreenVoyager

object SplashScreenVoyager : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        SplashScreen {
            navigator.push(HomeScreenVoyager)
        }
    }

}