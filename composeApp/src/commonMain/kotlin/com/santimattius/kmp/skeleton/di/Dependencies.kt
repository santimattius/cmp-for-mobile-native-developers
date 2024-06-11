package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.di.platformModule
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
}


fun applicationModules() = listOf(platformModule, homeModule)