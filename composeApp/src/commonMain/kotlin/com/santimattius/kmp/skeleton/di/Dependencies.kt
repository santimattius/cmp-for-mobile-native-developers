package com.santimattius.kmp.skeleton.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.santimattius.kmp.di.SharedModule
import com.santimattius.kmp.skeleton.features.favorites.FavoritesViewModel
import com.santimattius.kmp.skeleton.features.home.HomeViewModel

object AppModule {
    fun createHomeViewModel(): HomeViewModel {
        return HomeViewModel(
            getAllCharacters = SharedModule.provideGetAllCharacters(),
            refreshCharacters = SharedModule.provideRefreshCharacters(),
            addToFavorite = SharedModule.provideAddToFavorite(),
            removeFromFavorite = SharedModule.provideRemoveFromFavorites()
        )
    }

    fun createFavoritesViewModel(): FavoritesViewModel {
        return FavoritesViewModel(
            characterRepository = SharedModule.repository()
        )
    }
}


val LocalAppModule = staticCompositionLocalOf<AppModule> {
    error("LocalAppModule not provided")
}

@Composable
fun DiContainer(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppModule provides AppModule, content)
}