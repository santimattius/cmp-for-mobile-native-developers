package com.santimattius.kmp.skeleton.features.favorites

import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.arch.UiState

data class FavoritesUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
) : UiState
