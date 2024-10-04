package com.santimattius.kmp.skeleton.data

import com.santimattius.kmp.data.CharactersResponse
import com.santimattius.kmp.data.NetworkCharacter

object CharacterMother {
    private val jsonLoader = JsonLoader()

    fun getCharacters(): List<NetworkCharacter> {
        return jsonLoader.load<CharactersResponse>("characters.json").results
    }
}