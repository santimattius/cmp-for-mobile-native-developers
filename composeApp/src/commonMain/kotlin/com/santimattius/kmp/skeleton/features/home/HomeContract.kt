package com.santimattius.kmp.skeleton.features.home

import com.santimattius.kmp.domain.Character
import com.santimattius.kmp.skeleton.core.arch.UiState

data class HomeUiState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
) : UiState
