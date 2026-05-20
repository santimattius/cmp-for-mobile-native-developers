package com.santimattius.kmp.skeleton.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.readValue
import platform.CoreGraphics.CGRectZero
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformWebView(url: String, modifier: Modifier) {
    UIKitView(
        modifier = modifier,
        factory = {
            val wk = WKWebView(
                frame = CGRectZero.readValue(),
                configuration = WKWebViewConfiguration(),
            )
            NSURL.URLWithString(url)?.let { wk.loadRequest(NSURLRequest(uRL = it)) }
            wk
        },
        update = { wk ->
            NSURL.URLWithString(url)?.let { wk.loadRequest(NSURLRequest(uRL = it)) }
        },
    )
}
