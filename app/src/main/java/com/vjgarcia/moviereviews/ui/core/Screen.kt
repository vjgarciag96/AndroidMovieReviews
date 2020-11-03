package com.vjgarcia.moviereviews.ui.core

import androidx.compose.runtime.Composable

@Composable
fun MovieReviewsScreen(content: @Composable () -> Unit) {
    MovieReviewsTheme {
        content()
    }
}