package com.santimattius.kmp.skeleton

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cmp_for_mobile_native_developers.composeapp.generated.resources.Res
import cmp_for_mobile_native_developers.composeapp.generated.resources.app_name
import com.santimattius.kmp.skeleton.core.ui.components.AppBar
import com.santimattius.kmp.skeleton.core.ui.components.AppBarIcon
import com.santimattius.kmp.skeleton.core.ui.components.AppBarIconModel
import com.santimattius.kmp.skeleton.navigation.Favorites
import com.santimattius.kmp.skeleton.navigation.Navigation
import org.jetbrains.compose.resources.stringResource

@Composable
fun RootScreen(appState: AppState = rememberAppState()) {
    Scaffold(
        topBar = {
            if (appState.showTopAppBar) {
                AppBar(
                    title = stringResource(Res.string.app_name),
                    navIcon = if (appState.showUpNavigation) {
                        AppBarIconModel(
                            icon = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "goto back",
                            action = appState::onUpClick
                        )
                    } else null,
                    actions = {
                        AppBarIcon(
                            navIcon = AppBarIconModel(
                                icon = Icons.Default.Favorite,
                                contentDescription = "Favorite",
                                action = {
                                    appState.onNavItemClick(Favorites)
                                }
                            )
                        )
                    }
                )
            }
        }
    ) { padding ->
        Navigation(
            modifier = Modifier.fillMaxSize().padding(padding),
            navController = appState.navController
        )
    }
}