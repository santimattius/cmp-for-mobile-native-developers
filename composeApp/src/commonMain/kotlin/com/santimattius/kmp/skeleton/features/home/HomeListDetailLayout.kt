package com.santimattius.kmp.skeleton.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.ui.testing.TestTags
import com.santimattius.kmp.skeleton.features.detail.CharacterDetailRoute
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeListDetailRoute(
    onNavigateToDetail: (String) -> Unit = {},
    onOpenFavorites: () -> Unit = {},
    onOpenRepository: () -> Unit = {},
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navigator = rememberListDetailPaneScaffoldNavigator<String>()
    val scope = rememberCoroutineScope()

    HomeListDetailLayout(
        state = state,
        onRefresh = viewModel::refresh,
        navigator = navigator,
        onCharacterClick = { character ->
            val id = character.id.toString()
            val isExpanded = navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded
            if (isExpanded) {
                scope.launch { navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, id) }
            } else {
                onNavigateToDetail(id)
            }
        },
        onFavoriteClick = viewModel::addToFavorite,
        onOpenFavorites = onOpenFavorites,
        onOpenRepository = onOpenRepository,
        detailContent = { id ->
            CharacterDetailRoute(
                id = id,
                onBack = { scope.launch { navigator.navigateBack() } },
            )
        },
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeListDetailLayout(
    state: HomeUiState,
    onCharacterClick: (Character) -> Unit,
    onFavoriteClick: (Character) -> Unit,
    onRefresh: () -> Unit,
    navigator: ThreePaneScaffoldNavigator<String>,
    detailContent: @Composable (id: String) -> Unit,
    onOpenFavorites: () -> Unit = {},
    onOpenRepository: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            AnimatedPane {
                HomeScreen(
                    state = state,
                    onRefresh = onRefresh,
                    onCharacterClick = onCharacterClick,
                    onFavoriteClick = onFavoriteClick,
                    onOpenFavorites = onOpenFavorites,
                    onOpenRepository = onOpenRepository,
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val selectedId = navigator.currentDestination?.contentKey as? String
                if (selectedId != null) {
                    detailContent(selectedId)
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag(TestTags.HomeDetailPlaceholder),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("Select a character")
                    }
                }
            }
        },
        modifier = modifier,
    )
}
