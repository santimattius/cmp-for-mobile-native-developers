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
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import kotlin.native.concurrent.ThreadLocal


@ThreadLocal
@OptIn(InternalCoroutinesApi::class)
object CoreModule : SynchronizedObject() {

    private var httpClient: HttpClient? = null

    @OptIn(InternalCoroutinesApi::class)
    fun httpClient(): HttpClient {
        synchronized(this) {
            return httpClient ?: apiClient("https://rickandmortyapi.com").also { httpClient = it }
        }
    }
}

expect val sqlDriver: SqlDriver

@ThreadLocal
@OptIn(InternalCoroutinesApi::class)
object SharedModule : SynchronizedObject() {
    private var db: CharactersDatabase? = null
    private var remoteDataSource: CharacterNetworkDataSource? = null
    private var localDataSource: CharacterLocalDataSource? = null


    private var repository: CharacterRepository? = null

    fun provideGetAllCharacters(): GetAllCharacters {
        return GetAllCharacters(repository())
    }

    fun provideFindCharacterById(): FindCharacterById {
        return FindCharacterById(repository())
    }

    fun provideRefreshCharacters(): RefreshCharacters {
        return RefreshCharacters(repository())
    }

    fun provideAddToFavorite(): AddToFavorite {
        return AddToFavorite(repository())
    }

    fun provideRemoveFromFavorites(): RemoveFromFavorites {
        return RemoveFromFavorites(repository())
    }

    private fun remoteDataSource(): CharacterNetworkDataSource {
        synchronized(this) {
            return remoteDataSource
                ?: KtorCharacterNetworkDataSource(CoreModule.httpClient()).also {
                    remoteDataSource = it
                }
        }
    }

    private fun localDataSource(): CharacterLocalDataSource {
        synchronized(this) {
            return localDataSource ?: SQLDelightCharacterLocalDataSource(db()).also {
                localDataSource = it
            }
        }
    }

    fun db(): CharactersDatabase {
        synchronized(this) {
            return db ?: createDatabase(sqlDriver).also { db = it }
        }
    }

    fun repository(): CharacterRepository {
        synchronized(this) {
            return repository ?: CharacterRepository(localDataSource(), remoteDataSource()).also {
                repository = it
            }
        }
    }
}
