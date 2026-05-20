package com.santimattius.kmp.skeleton.features.repository

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import cmp_for_mobile_native_developers.composeapp.generated.resources.Res
import cmp_for_mobile_native_developers.composeapp.generated.resources.repository_back_content_description
import cmp_for_mobile_native_developers.composeapp.generated.resources.repository_screen_title
import com.santimattius.kmp.skeleton.core.ui.components.AppScaffold
import com.santimattius.kmp.skeleton.core.ui.components.PlatformWebView
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags
import org.jetbrains.compose.resources.stringResource

private const val REPOSITORY_URL =
    "https://github.com/santimattius/cmp-for-mobile-native-developers"

@Composable
fun RepositoryScreenRoute(onBack: () -> Unit) {
    RepositoryScreen(url = REPOSITORY_URL, onBack = onBack)
}

@Composable
fun RepositoryScreen(
    url: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        modifier = modifier.testTag(TestTags.RepositoryScreen),
        topBar = { RepositoryTopAppBar(onBack = onBack) },
    ) { innerPadding ->
        PlatformWebView(
            url = url,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag(TestTags.RepositoryWebView),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryTopAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(Res.string.repository_screen_title)) },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(Res.string.repository_back_content_description),
                )
            }
        },
    )
}
