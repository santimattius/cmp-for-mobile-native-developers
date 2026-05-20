package com.santimattius.kmp.skeleton.core.ui.testing

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestTagsUniqueTest {

    @Test
    fun testTagConstantsAreUnique() {
        val constantTags = listOf(
            TestTags.HomeScreen,
            TestTags.HomeGrid,
            TestTags.HomeLoading,
            TestTags.HomeError,
            TestTags.HomeEmpty,
            TestTags.FavoritesScreen,
            TestTags.FavoritesList,
            TestTags.FavoritesEmpty,
            TestTags.DetailScreen,
            TestTags.DetailLoading,
            TestTags.DetailError,
            TestTags.DetailWebView,
            TestTags.DetailBack,
            TestTags.DetailFavToggle,
            TestTags.AppScaffold,
            TestTags.SnackbarHost,
            TestTags.HomeOpenRepository,
            TestTags.RepositoryScreen,
            TestTags.RepositoryWebView,
        )
        val distinctCount = constantTags.distinct().size
        assertEquals(
            expected = constantTags.size,
            actual = distinctCount,
            message = "TestTags constants must all be unique. Found ${constantTags.size - distinctCount} duplicate(s).",
        )
    }

    @Test
    fun homeItemTagGeneratesUniqueTagPerId() {
        val tag1 = TestTags.homeItem("1")
        val tag2 = TestTags.homeItem("2")
        val tagToggle = TestTags.homeFavoriteToggle("1")
        assertTrue(tag1 != tag2, "homeItem tags must differ for different ids")
        assertTrue(tag1 != tagToggle, "homeItem and homeFavoriteToggle must differ for same id")
    }

    @Test
    fun favoriteTagGeneratesUniqueTagPerId() {
        val tag1 = TestTags.favoriteItem("1")
        val tag2 = TestTags.favoriteItem("2")
        val tagToggle = TestTags.favoriteToggle("1")
        assertTrue(tag1 != tag2, "favoriteItem tags must differ for different ids")
        assertTrue(tag1 != tagToggle, "favoriteItem and favoriteToggle must differ for same id")
    }
}
