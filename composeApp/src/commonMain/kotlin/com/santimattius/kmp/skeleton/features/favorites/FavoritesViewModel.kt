package com.santimattius.kmp.skeleton.features.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.domain.AddToFavorite
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.domain.GetFavoriteCharacters
import com.santimattius.kmp.domain.RemoveFromFavorites
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    getFavoriteCharacters: GetFavoriteCharacters,
    private val addToFavorite: AddToFavorite,
    private val removeFromFavorites: RemoveFromFavorites,
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> = getFavoriteCharacters()
        .map { characters ->
            FavoritesUiState(
                characters = characters,
                isLoading = false,
            )
        }
        .catch {
            emit(FavoritesUiState(isLoading = false))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = FavoritesUiState(isLoading = true),
        )

    fun toggleFavorite(character: Character) {
        viewModelScope.launch {
            if (character.isFavorite) {
                removeFromFavorites(character.id)
            } else {
                addToFavorite(character.id)
            }
        }
    }
}
