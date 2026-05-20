package com.santimattius.kmp.skeleton.core.ui.testing

object TestTags {

    // Home
    const val HomeScreen = "home_screen"
    const val HomeGrid = "home_grid"
    const val HomeLoading = "home_loading"
    const val HomeError = "home_error"
    const val HomeEmpty = "home_empty"
    const val HomeOpenRepository = "home_open_repository"
    fun homeItem(id: String) = "home_item_$id"
    fun homeFavoriteToggle(id: String) = "home_fav_toggle_$id"

    // Favorites
    const val FavoritesScreen = "favorites_screen"
    const val FavoritesList = "favorites_list"
    const val FavoritesEmpty = "favorites_empty"
    fun favoriteItem(id: String) = "fav_item_$id"
    fun favoriteToggle(id: String) = "fav_toggle_$id"

    // Detail
    const val DetailScreen = "detail_screen"
    const val DetailLoading = "detail_loading"
    const val DetailError = "detail_error"
    const val DetailWebView = "detail_webview"
    const val DetailBack = "detail_back"
    const val DetailFavToggle = "detail_fav_toggle"

    // Adaptive / ListDetail
    const val HomeDetailPlaceholder = "home_detail_placeholder"

    // Repository
    const val RepositoryScreen = "repository_screen"
    const val RepositoryWebView = "repository_webview"

    // Global
    const val AppScaffold = "app_scaffold"
    const val SnackbarHost = "snackbar_host"
}
