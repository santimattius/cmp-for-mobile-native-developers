package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.di.dataLayerModules
import com.santimattius.kmp.skeleton.features.detail.CharacterDetailViewModel
import com.santimattius.kmp.skeleton.features.favorites.FavoritesViewModel
import com.santimattius.kmp.skeleton.features.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        HomeViewModel(
            getAllCharacters = get(),
            refreshCharacters = get(),
            addToFavorite = get(),
            removeFromFavorite = get(),
        )
    }

    viewModel {
        FavoritesViewModel(
            getFavoriteCharacters = get(),
            addToFavorite = get(),
            removeFromFavorites = get(),
        )
    }

    viewModel { (id: String) ->
        CharacterDetailViewModel(id = id, findCharacterById = get())
    }
}

fun applicationModules() = dataLayerModules() + presentationModule
