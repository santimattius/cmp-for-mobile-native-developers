package com.santimattius.kmp.skeleton

import App
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
            StatusBar()
        }
    }
}

@Composable
fun StatusBar() {
    val view = LocalView.current
    val primaryColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f).toArgb()
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = primaryColor
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }
}
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}