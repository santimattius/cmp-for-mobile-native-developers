package com.santimattius.kmp.skeleton.core.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

typealias ComposableFactory = @Composable () -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: String = "",
    navIcon: AppBarIconModel? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    titleContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = if (navIcon == null) {
            composableFactory { }
        } else {
            composableFactory { AppBarIcon(navIcon) }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
            navigationIconContentColor = titleContentColor,
            actionIconContentColor = titleContentColor,
        ),
        actions = actions
    )
}

@Composable
internal fun AppBarIcon(navIcon: AppBarIconModel) {
    IconButton(onClick = { navIcon.action() }) {
        Icon(imageVector = navIcon.icon, contentDescription = navIcon.contentDescription)
    }
}

fun composableFactory(create: @Composable () -> Unit): ComposableFactory {
    val factory: @Composable () -> Unit = {
        create()
    }
    return factory
}

data class AppBarIconModel(
    val icon: ImageVector,
    val contentDescription: String,
    val action: () -> Unit,
)
