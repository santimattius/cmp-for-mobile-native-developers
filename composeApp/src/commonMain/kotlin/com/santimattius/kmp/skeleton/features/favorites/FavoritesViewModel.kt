package com.santimattius.kmp.skeleton.features.favorites


import com.santimattius.kmp.data.CharacterRepository
import com.santimattius.kmp.domain.Character
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class FavoritesViewModel(
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    var characters = characterRepository.allFavoritesCharacters.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000L),
        initialValue = emptyList()
    )

    fun addToFavorites(character: Character) {
        viewModelScope.launch {
            if (character.isFavorite) {
                characterRepository.removeFromFavorite(character.id)
            } else {
                characterRepository.addToFavorite(character.id)
            }
        }
    }
}