package com.santimattius.kmp.di

import app.cash.sqldelight.db.SqlDriver
import com.santimattius.kmp.CharactersDatabase
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.data.db.createDatabase
import com.santimattius.kmp.data.network.apiClient
import com.santimattius.kmp.data.sources.CharacterLocalDataSource
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource
import com.santimattius.kmp.data.sources.ktor.KtorCharacterNetworkDataSource
import com.santimattius.kmp.data.sources.sqldelight.SQLDelightCharacterLocalDataSource
import com.santimattius.kmp.domain.AddToFavorite
import com.santimattius.kmp.domain.FindCharacterById
import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.domain.RemoveFromFavorites
import io.ktor.client.HttpClient
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val coreModule by DI.Module("CoreModule") {
    bindSingleton { apiClient("https://rickandmortyapi.com") }
}

val sharedModule by DI.Module("SharedModule") {
    bindSingleton { KtorCharacterNetworkDataSource(instance<HttpClient>()) }
    bindSingleton { createDatabase(instance<SqlDriver>()) }
    bindSingleton { SQLDelightCharacterLocalDataSource(db = instance<CharactersDatabase>()) }
    bindSingleton {
        CharacterRepository(
            local = instance<CharacterLocalDataSource>(),
            network = instance<CharacterNetworkDataSource>()
        )
    }
    bindFactory<Unit, GetAllCharacters> { GetAllCharacters(instance<CharacterRepository>()) }
    bindFactory<Unit, FindCharacterById> { FindCharacterById(instance<CharacterRepository>()) }
    bindFactory<Unit, RefreshCharacters> { RefreshCharacters(instance<CharacterRepository>()) }

    bindFactory<Unit, AddToFavorite> { AddToFavorite(instance<CharacterRepository>()) }
    bindFactory<Unit, RemoveFromFavorites> { RemoveFromFavorites(instance<CharacterRepository>()) }
}

expect val platformModule: DI.Module

fun dataModule() = listOf(sharedModule, coreModule, platformModule)