package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.di.dataModule
import com.santimattius.kmp.skeleton.features.favorites.FavoritesScreenModel
import com.santimattius.kmp.skeleton.features.home.HomeScreenModel
import org.koin.dsl.module

val homeModule = module {
    factory {
        HomeScreenModel(
            getAllCharacters = get(),
            refreshCharacters = get(),
            addToFavorite = get(),
            removeFromFavorite = get()
        )
    }
    factory {
        FavoritesScreenModel(characterRepository = get())
    }
}


fun applicationModules() = listOf(homeModule) + dataModule()