package com.santimattius.kmp.skeleton.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.santimattius.kmp.skeleton.features.detail.CharacterDetailRoute
import com.santimattius.kmp.skeleton.features.favorites.FavoriteRoute
import com.santimattius.kmp.skeleton.features.home.HomeListDetailRoute
import com.santimattius.kmp.skeleton.features.repository.RepositoryScreenRoute
import com.santimattius.kmp.skeleton.features.splash.SplashScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 * [SavedStateConfiguration] that registers all [Destination] subclasses under the [NavKey]
 * open polymorphic hierarchy.
 *
 * Required by [rememberNavBackStack] on non-Android platforms (iOS, desktop). On Android a
 * platform-specific overload handles registration internally via reflection; on Kotlin/Native
 * the common overload requires an explicit [SavedStateConfiguration].
 *
 * Navigation3 runtime 1.1.1 — see RememberNavBackStack.kt / RememberNavBackStack.android.kt.
 */
private val navSavedStateConfiguration = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Splash::class)
            subclass(Home::class)
            subclass(Favorites::class)
            subclass(CharacterDetail::class)
            subclass(Repository::class)
        }
    }
}

/**
 * Root navigation composable.
 *
 * @param deepLinkUri Optional URI received from a platform deep-link (e.g. `kmpapp://character/42`).
 *   When non-null, [parseDeepLink] resolves it to a [Destination] and that destination is pushed
 *   onto the back stack after Splash clears. Forwarding on iOS requires Swift SceneDelegate work
 *   (see TODO in iosApp/iosApp/iOSApp.swift).
 */
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    deepLinkUri: String? = null,
) {
    // rememberNavBackStack(SavedStateConfiguration, vararg NavKey) — common multiplatform overload.
    // The Android-specific overload (vararg only) handles polymorphic registration internally via
    // reflection, but Kotlin/Native requires explicit SavedStateConfiguration (Nav3 runtime 1.1.1).
    val backStack = rememberNavBackStack(navSavedStateConfiguration, Splash)

    // When a deep-link URI is provided, parse it and push the destination once the
    // back stack has been set to [Home] (i.e., after Splash has completed its navigation).
    LaunchedEffect(deepLinkUri) {
        if (deepLinkUri != null) {
            val destination = parseDeepLink(deepLinkUri)
            if (destination != null) {
                // Wait until Splash has navigated away (back stack head == Home).
                // We push the destination immediately; if Splash hasn't fired yet,
                // HomeScreenRoute's nav intent will land right after Home is shown.
                backStack.clear()
                backStack.add(Home)
                backStack.add(destination)
            }
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Splash> {
                SplashScreen(navigate = {
                    backStack.clear()
                    backStack.add(Home)
                })
            }
            entry<Home> {
                HomeListDetailRoute(
                    onNavigateToDetail = { id -> backStack.add(CharacterDetail(id)) },
                    onOpenFavorites = { backStack.add(Favorites) },
                    onOpenRepository = { backStack.add(Repository) },
                )
            }
            entry<Favorites> {
                FavoriteRoute(
                    onNavigateToDetail = { id -> backStack.add(CharacterDetail(id)) },
                    onBack = { backStack.removeLastOrNull() },
                )
            }
            entry<CharacterDetail> { key ->
                CharacterDetailRoute(
                    id = key.id,
                    onBack = { backStack.removeLastOrNull() },
                )
            }
            entry<Repository> {
                RepositoryScreenRoute(
                    onBack = { backStack.removeLastOrNull() },
                )
            }
        },
    )
}
