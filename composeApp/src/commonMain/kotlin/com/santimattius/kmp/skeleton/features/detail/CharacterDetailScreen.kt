package com.santimattius.kmp.skeleton.features.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.kmp.skeleton.core.ui.components.AppScaffold
import com.santimattius.kmp.skeleton.core.ui.components.ErrorContent
import com.santimattius.kmp.skeleton.core.ui.components.LoadingContent
import com.santimattius.kmp.skeleton.core.ui.components.NetworkImage
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags
import com.santimattius.kmp.skeleton.core.ui.themes.AppSpacing
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailRoute(
    id: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<CharacterDetailViewModel> { parametersOf(id) }
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    CharacterDetailScreen(
        state = state,
        onRetry = viewModel::retry,
        onBack = onBack,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    state: CharacterDetailUiState,
    onRetry: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        modifier = modifier.testTag(TestTags.DetailScreen),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.character?.name ?: "Loading...")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag(TestTags.DetailBack),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        when {
            state.isLoading -> LoadingContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .testTag(TestTags.DetailLoading),
            )

            state.error != null -> ErrorContent(
                message = state.error,
                onRetry = onRetry,
                modifier = Modifier
                    .padding(paddingValues)
                    .testTag(TestTags.DetailError),
            )

            state.character != null -> CharacterDetailContent(
                name = state.character.name,
                imageUrl = state.character.image,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(AppSpacing.md),
            )
        }
    }
}

@Composable
private fun CharacterDetailContent(
    name: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        NetworkImage(
            imageUrl = imageUrl,
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
        )
        Spacer(modifier = Modifier.height(AppSpacing.md))
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}
