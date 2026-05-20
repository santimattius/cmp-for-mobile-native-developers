package com.santimattius.kmp.skeleton.features.detail

import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.arch.UiState

data class CharacterDetailUiState(
    val isLoading: Boolean = true,
    val character: Character? = null,
    val error: String? = null,
) : UiState
