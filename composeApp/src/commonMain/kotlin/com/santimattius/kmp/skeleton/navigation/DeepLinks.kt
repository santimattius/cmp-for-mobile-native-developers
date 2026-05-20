package com.santimattius.kmp.skeleton.navigation

/**
 * Parses a deep-link URI into a [Destination], or returns null if the URI is not recognized.
 *
 * Supported scheme: `kmpapp://character/{id}` → [CharacterDetail]
 *
 * iOS deep-link forwarding (SceneDelegate / onOpenURL) is out of scope for this task.
 * See TODO in iosApp/iosApp/iOSApp.swift.
 */
fun parseDeepLink(uri: String): Destination? = when {
    uri.startsWith("kmpapp://character/") -> {
        val id = uri.substringAfterLast('/')
        if (id.isNotBlank()) CharacterDetail(id) else null
    }
    else -> null
}
