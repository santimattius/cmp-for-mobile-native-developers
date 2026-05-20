package com.santimattius.kmp.skeleton.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination : NavKey

@Serializable
data object Splash : Destination

@Serializable
data object Home : Destination

@Serializable
data object Favorites : Destination

@Serializable
data class CharacterDetail(val id: String) : Destination

@Serializable
data object Repository : Destination
