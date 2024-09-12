package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.di.dataModule
import com.santimattius.kmp.skeleton.features.favorites.FavoritesViewModel
import com.santimattius.kmp.skeleton.features.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        HomeViewModel(
            getAllCharacters = get(),
            refreshCharacters = get(),
            addToFavorite = get(),
            removeFromFavorite = get()
        )
    }

    viewModel {
        FavoritesViewModel(characterRepository = get())
    }
}


fun applicationModules() = listOf(homeModule) + dataModule()