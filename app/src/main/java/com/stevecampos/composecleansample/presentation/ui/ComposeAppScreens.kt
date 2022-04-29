package com.stevecampos.composecleansample.presentation.ui

sealed class ComposeAppScreens(val route: String) {
    object UserListScreen : ComposeAppScreens("users")
    object GetPostsScreen : ComposeAppScreens("posts/")
}
