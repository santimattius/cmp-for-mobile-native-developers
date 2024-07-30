package com.santimattius.kmp.skeleton.features.favorites

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesScreenModel(
    private val characterRepository: CharacterRepository,
) : StateScreenModel<Unit>(Unit) {

    var characters = characterRepository.allFavoritesCharacters.stateIn(
        scope = screenModelScope,
        started = SharingStarted.WhileSubscribed(1000L),
        initialValue = emptyList()
    )

    fun addToFavorites(character: Character) {
        screenModelScope.launch {
            if (character.isFavorite) {
                characterRepository.removeFromFavorite(character.id)
            } else {
                characterRepository.addToFavorite(character.id)
            }
        }
    }
}