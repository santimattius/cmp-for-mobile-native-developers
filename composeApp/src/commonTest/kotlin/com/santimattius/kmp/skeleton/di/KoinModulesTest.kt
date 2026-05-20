package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.skeleton.features.detail.CharacterDetailViewModel
import com.santimattius.kmp.skeleton.features.favorites.FavoritesViewModel
import com.santimattius.kmp.skeleton.features.home.HomeViewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import kotlin.test.Test
import kotlin.test.assertNotNull

class KoinModulesTest {

    @Test
    fun koinApplicationStartsWithoutException() {
        val app = koinApplication {
            allowOverride(true)
            modules(applicationModules() + testingModule)
        }
        assertNotNull(app.koin)
    }

    @Test
    fun homeViewModelResolvesFromDiGraph() {
        val app = koinApplication {
            allowOverride(true)
            modules(applicationModules() + testingModule)
        }
        val viewModel = app.koin.get<HomeViewModel>()
        assertNotNull(viewModel)
    }

    @Test
    fun favoritesViewModelResolvesFromDiGraph() {
        val app = koinApplication {
            allowOverride(true)
            modules(applicationModules() + testingModule)
        }
        val viewModel = app.koin.get<FavoritesViewModel>()
        assertNotNull(viewModel)
    }

    @Test
    fun characterDetailViewModelResolvesFromDiGraph() {
        val koin = koinApplication {
            allowOverride(true)
            modules(applicationModules() + testingModule)
        }.koin
        val vm = koin.get<CharacterDetailViewModel> { parametersOf("1") }
        assertNotNull(vm)
    }
}
