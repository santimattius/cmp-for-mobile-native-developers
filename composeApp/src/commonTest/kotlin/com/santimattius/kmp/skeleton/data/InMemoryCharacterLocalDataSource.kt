package com.santimattius.kmp.skeleton.data

import com.santimattius.kmp.data.sources.CharacterLocalDataSource
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class InMemoryCharacterLocalDataSource(
    initialCharacters: List<Character> = emptyList()
) : CharacterLocalDataSource {

    private val mutex = Mutex()
    private val _characters = MutableStateFlow(initialCharacters)

    override val all: Flow<List<Character>>
        get() = _characters

    override val favorites: Flow<List<Character>>
        get() = _characters.map { characters -> characters.filter { it.isFavorite } }

    override suspend fun find(id: Long): Result<Character> = runCatching {
        _characters.value.firstOrNull { it.id == id } ?: throw NoSuchElementException()
    }

    override suspend fun addToFavorite(id: Long): Result<Unit> = runCatching {
        updateFavorite(id, true)
    }

    override suspend fun removeToFavorite(id: Long): Result<Unit> = runCatching {
        updateFavorite(id, false)
    }

    override suspend fun insert(character: Character): Result<Unit> = runCatching {
        mutex.withLock {
            val characters = _characters.value.toMutableList()
            characters.add(character)
            _characters.value = characters
        }
    }

    override suspend fun insertAll(characters: List<Character>): Result<Unit> = runCatching {
        mutex.withLock {
            val currentCharacters = _characters.value.toMutableList()
            currentCharacters.addAll(characters)
            _characters.value = currentCharacters
        }
    }

    override suspend fun clear(): Result<Unit> = runCatching {
        _characters.value = emptyList()
    }

    private suspend fun updateFavorite(id: Long, isFavorite: Boolean) {
        mutex.withLock {
            val characters = _characters.value.toMutableList()
            val indexOfFirst = characters.indexOfFirst { it.id == id }
            if (indexOfFirst !in characters.indices) throw NoSuchElementException()
            val character = characters[indexOfFirst]
            val characterUpdated = character.copy(isFavorite = isFavorite)
            characters.removeAt(indexOfFirst)
            characters.add(characterUpdated)
            _characters.value = characters
        }
    }
}