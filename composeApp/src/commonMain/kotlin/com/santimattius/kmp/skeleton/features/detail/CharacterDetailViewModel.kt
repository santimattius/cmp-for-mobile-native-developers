package com.santimattius.kmp.skeleton.features.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.domain.FindCharacterById
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterDetailViewModel(
    private val id: String,
    private val findCharacterById: FindCharacterById,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CharacterDetailUiState(isLoading = true))
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun retry() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        load()
    }

    private fun load() {
        viewModelScope.launch {
            val characterId = id.toLongOrNull()
            if (characterId == null) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Invalid character id: $id")
                }
                return@launch
            }
            findCharacterById(characterId)
                .onSuccess { character ->
                    _uiState.update {
                        it.copy(isLoading = false, character = character, error = null)
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(isLoading = false, error = throwable.message ?: "Unknown error")
                    }
                }
        }
    }
}
