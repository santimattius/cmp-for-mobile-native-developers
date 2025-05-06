package com.santimattius.kmp.skeleton.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun <T : Any> NavHostController.navigatePoppingUpToStartDestination(route: T) {
    navigate(route) {
        popUpTo(graph.findStartDestination().navigatorName) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}