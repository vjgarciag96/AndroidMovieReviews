package com.vjgarcia.moviereviews.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview

@Composable
fun MovieReviewDetail(movieReviewId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Text(
            text = "Movie review $movieReviewId",
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun MovieReviewDetailPreview() {
    MovieReviewDetail(movieReviewId = 5)
}