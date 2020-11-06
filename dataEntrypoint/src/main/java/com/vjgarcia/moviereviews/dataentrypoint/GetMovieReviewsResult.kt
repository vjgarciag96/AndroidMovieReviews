package com.vjgarcia.moviereviews.dataentrypoint

sealed class GetMovieReviewsResult {
    data class Success(val movieReviews: List<MovieReviewData>) : GetMovieReviewsResult()
    object Error : GetMovieReviewsResult()
}