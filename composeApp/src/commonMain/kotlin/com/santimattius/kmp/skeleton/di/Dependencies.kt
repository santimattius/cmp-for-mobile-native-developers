package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.di.dataModule
import com.santimattius.kmp.skeleton.features.favorites.FavoritesViewModel
import com.santimattius.kmp.skeleton.features.home.HomeViewModel
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.instance

val appModule = DI.Module("AppModule") {
    bindFactory<Unit, HomeViewModel> {
        HomeViewModel(
            getAllCharacters = instance(),
            refreshCharacters = instance(),
            addToFavorite = instance(),
            removeFromFavorite = instance()
        )
    }

    bindFactory<Unit, FavoritesViewModel> {
        FavoritesViewModel(characterRepository = instance())
    }
}


fun applicationModules() = listOf(appModule) + dataModule()