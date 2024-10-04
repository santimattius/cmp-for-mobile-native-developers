package com.santimattius.kmp.skeleton.data

import com.santimattius.kmp.data.NetworkCharacter
import com.santimattius.kmp.data.sources.CharacterNetworkDataSource

class MockCharacterNetworkDataSource : CharacterNetworkDataSource {

    override suspend fun find(id: Long): Result<NetworkCharacter> {
        return all().fold(
            onSuccess = {
                val character = it.firstOrNull { element -> element.id == id }
                if (character != null) {
                    Result.success(character)
                } else {
                    Result.failure(NoSuchElementException("Character not exists"))
                }
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun all(): Result<List<NetworkCharacter>> = runCatching {
        CharacterMother.getCharacters()
    }
}