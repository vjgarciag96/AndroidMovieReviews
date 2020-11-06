package com.vjgarcia.moviereviews.domain

import kotlinx.coroutines.flow.Flow

class GetMovieReviewsFeedState(
    private val movieReviewsFeedBusinessUnit: MovieReviewsFeedBusinessUnit
) {

    operator fun invoke(): Flow<MovieReviewsFeedState> = movieReviewsFeedBusinessUnit.movieReviewsFeedState
}

class LoadInitialMovieReviews(
    private val movieReviewsFeedBusinessUnit: MovieReviewsFeedBusinessUnit
) {

    suspend operator fun invoke() {
        movieReviewsFeedBusinessUnit.loadInitialMovieReviews()
    }
}

class LoadMoreMovieReviews(
    private val movieReviewsFeedBusinessUnit: MovieReviewsFeedBusinessUnit
) {

    suspend operator fun invoke() {
        movieReviewsFeedBusinessUnit.loadMoreMovieReviews()
    }
}