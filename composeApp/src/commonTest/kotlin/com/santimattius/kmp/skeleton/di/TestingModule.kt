package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.data.sources.CharacterLocalDataSource
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource
import com.santimattius.kmp.skeleton.data.InMemoryCharacterLocalDataSource
import com.santimattius.kmp.skeleton.data.MockCharacterNetworkDataSource
import org.koin.dsl.module

val testingModule = module {
    single<CharacterNetworkDataSource> { MockCharacterNetworkDataSource() }
    single<CharacterLocalDataSource> {
        InMemoryCharacterLocalDataSource()
    }
}