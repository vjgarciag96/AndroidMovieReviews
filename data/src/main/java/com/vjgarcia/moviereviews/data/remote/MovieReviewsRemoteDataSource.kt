package com.vjgarcia.moviereviews.data.remote

class MovieReviewsRemoteDataSource(
    private val movieReviewsApiService: MovieReviewsApiService
) {

    suspend fun get(offset: Int? = null): MovieReviewsDto? =
        try {
            val movieReviewsResponse = movieReviewsApiService.get(offset)
            if (movieReviewsResponse.status == "OK") {
                movieReviewsResponse
            } else {
                null
            }
        } catch (throwable: Throwable) {
            null
        }
}