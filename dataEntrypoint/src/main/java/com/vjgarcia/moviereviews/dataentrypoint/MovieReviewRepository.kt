package com.vjgarcia.moviereviews.dataentrypoint

interface MovieReviewRepository {
    suspend fun get(offset: Int = 0, limit: Int = 20): GetMovieReviewsResult
}