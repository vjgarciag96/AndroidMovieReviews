package com.vjgarcia.moviereviews

import MovieReviewsFeed
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import com.vjgarcia.moviereviews.presentation.MovieReviewsFeedViewModel

class MainActivity : AppCompatActivity() {

    private val movieReviewsViewModel by viewModels<MovieReviewsFeedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MovieReviewsFeed(viewModel = movieReviewsViewModel) }
    }
}

