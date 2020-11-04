package com.vjgarcia.moviereviews.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MovieReviewsApiService {

    @GET(MovieReviewsApiServicePaths.criticsPicks)
    suspend fun get(@Query("offset") offset: Int? = null): MovieReviewsDto
}