package com.vjgarcia.moviereviews.ui

import androidx.compose.runtime.Composable

@Composable
fun MovieReviewsScreen(content: @Composable () -> Unit) {
    MovieReviewsTheme {
        content()
    }
}