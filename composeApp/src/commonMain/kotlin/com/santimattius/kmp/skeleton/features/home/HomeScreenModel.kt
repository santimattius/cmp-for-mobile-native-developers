package com.santimattius.kmp.skeleton.features.home

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
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


data class HomeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Character> = emptyList(),
)

class HomeScreenModel(
    getAllCharacters: GetAllCharacters,
    private val refreshCharacters: RefreshCharacters,
    private val addToFavorite: AddToFavorite,
    private val removeFromFavorite: RemoveFromFavorites,
) : StateScreenModel<HomeUiState>(HomeUiState()) {

    var uiState: StateFlow<HomeUiState> = getAllCharacters().map {
        HomeUiState(
            isLoading = false,
            hasError = false,
            data = it
        )
    }.stateIn(
        scope = screenModelScope,
        started = SharingStarted.WhileSubscribed(1000L),
        initialValue = HomeUiState(isLoading = true)
    )

    init {
        refresh()
    }

    private fun refresh() {
        screenModelScope.launch {
            refreshCharacters.invoke()
        }
    }

    fun addToFavorites(character: Character) {
        screenModelScope.launch {
            if (character.isFavorite) {
                removeFromFavorite(character.id)
            } else {
                addToFavorite(character.id)
            }
        }
    }
}