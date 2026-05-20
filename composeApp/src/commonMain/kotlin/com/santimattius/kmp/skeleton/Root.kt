package com.santimattius.kmp.skeleton

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.santimattius.kmp.skeleton.navigation.Navigation

@Composable
fun RootScreen(deepLinkUri: String? = null) {
    Navigation(modifier = Modifier.fillMaxSize(), deepLinkUri = deepLinkUri)
}
