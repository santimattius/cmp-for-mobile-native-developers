package com.santimattius.kmp.skeleton.features.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

object HomeScreenVoyager : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        HomeScreenRoute()
    }

}