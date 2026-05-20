package com.santimattius.kmp.skeleton.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.domain.AddToFavorite
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.domain.RemoveFromFavorites
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    getAllCharacters: GetAllCharacters,
    private val refreshCharacters: RefreshCharacters,
    private val addToFavorite: AddToFavorite,
    private val removeFromFavorite: RemoveFromFavorites,
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = getAllCharacters()
        .onStart { refresh() }
        .map { characters ->
            HomeUiState(
                isLoading = false,
                characters = characters,
                error = null,
            )
        }
        .catch { throwable ->
            emit(HomeUiState(error = throwable.message ?: "Unknown error"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeUiState(isLoading = true),
        )

    fun refresh() {
        viewModelScope.launch {
            refreshCharacters.invoke()
        }
    }

    fun addToFavorite(character: Character) {
        viewModelScope.launch {
            if (character.isFavorite) {
                removeFromFavorite(character.id)
            } else {
                addToFavorite(character.id)
            }
        }
    }
}
