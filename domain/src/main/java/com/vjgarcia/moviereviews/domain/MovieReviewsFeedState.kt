package com.vjgarcia.moviereviews.domain

import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewData

sealed class MovieReviewsFeedState {
    object Initial : MovieReviewsFeedState()

    data class InitialMovieReviewsLoaded(
        val movieReviews: List<MovieReviewData>
    ) : MovieReviewsFeedState()

    object InitialError : MovieReviewsFeedState()

    object LoadingMore : MovieReviewsFeedState()

    data class AdditionalMovieReviewsLoaded(
        val movieReviews: List<MovieReviewData>
    ) : MovieReviewsFeedState()

    object AdditionalMovieReviewsLoadError : MovieReviewsFeedState()
}