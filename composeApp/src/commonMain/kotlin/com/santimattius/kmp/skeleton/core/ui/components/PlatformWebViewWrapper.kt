package com.santimattius.kmp.skeleton.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun PlatformWebViewWrapper(url: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.testTag("platform_web_view_wrapper")) {
        PlatformWebView(url = url)
    }
}
