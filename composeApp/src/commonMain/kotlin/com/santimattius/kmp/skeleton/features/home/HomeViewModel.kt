package com.santimattius.kmp.skeleton.features.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.domain.AddToFavorite
import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.domain.GetAllCharacters
import com.santimattius.kmp.domain.RefreshCharacters
import com.santimattius.kmp.domain.RemoveFromFavorites
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Stable
data class HomeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Character> = emptyList(),
)

class HomeViewModel(
    getAllCharacters: GetAllCharacters,
    private val refreshCharacters: RefreshCharacters,
    private val addToFavorite: AddToFavorite,
    private val removeFromFavorite: RemoveFromFavorites,
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = getAllCharacters()
        .map {
            HomeUiState(
                isLoading = false,
                hasError = false,
                data = it
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = HomeUiState(isLoading = true)
        )

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            refreshCharacters.invoke()
        }
    }

    fun addToFavorites(character: Character) {
        viewModelScope.launch {
            if (character.isFavorite) {
                removeFromFavorite(character.id)
            } else {
                addToFavorite(character.id)
            }
        }
    }
}