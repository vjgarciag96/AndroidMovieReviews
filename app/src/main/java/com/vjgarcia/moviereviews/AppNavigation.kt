package com.vjgarcia.moviereviews

import androidx.navigation.NavController
import androidx.navigation.compose.navigate

class AppNavigation(
    private val navController: NavController
) {

    fun navigateToMovieReviewDetail(movieReviewId: Int) {
        navController.navigate("$MOVIE_REVIEW_DETAIL_PATH/$movieReviewId")
    }

    companion object {
        const val MOVIE_REVIEWS_FEED = "movieReviewsFeed"
        const val MOVIE_REVIEW_ID_ARG = "movieReviewId"
        private const val MOVIE_REVIEW_DETAIL_PATH = "movieReviewDetail"
        const val MOVIE_REVIEW_DETAIL = "$MOVIE_REVIEW_DETAIL_PATH/{$MOVIE_REVIEW_ID_ARG}"
    }
}