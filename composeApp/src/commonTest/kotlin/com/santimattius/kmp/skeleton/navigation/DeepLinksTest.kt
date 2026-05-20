package com.santimattius.kmp.skeleton.navigation

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class DeepLinksTest {

    @Test
    fun parseDeepLink_returnsCharacterDetail_forValidUri() {
        val result = parseDeepLink("kmpapp://character/42")
        assertEquals(CharacterDetail("42"), result)
    }

    @Test
    fun parseDeepLink_returnsNull_forUnknownScheme() {
        assertNull(parseDeepLink("https://example.com"))
    }

    @Test
    fun parseDeepLink_returnsNull_forEmptyId() {
        assertNull(parseDeepLink("kmpapp://character/"))
    }
}
