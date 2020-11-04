package com.vjgarcia.moviereviews.data.repository

import com.vjgarcia.moviereviews.data.local.MovieReviewDao
import com.vjgarcia.moviereviews.data.remote.MovieReviewsApiService
import com.vjgarcia.moviereviews.data.toMovieReviewEntity

class MovieReviewRepository(
    private val movieReviewDao: MovieReviewDao,
    private val movieReviewsApiService: MovieReviewsApiService
) {

    suspend fun get(offset: Int = 0, limit: Int = 20) : List<MovieReviewData> {
        if (offset == 0) {
            val remoteMovieReviews = movieReviewsApiService.get()
            if (remoteMovieReviews.status == "OK") {
                movieReviewDao.insertAll(remoteMovieReviews.results.map { it.toMovieReviewEntity() })
            }
        }

        val localMovieReviews = movieReviewDao.get(offset = offset, limit = limit)

        return if (localMovieReviews.size < limit && offset != 0) {
            val remoteMovieReviewsByOffset = movieReviewsApiService.get(offset = offset)
            if (remoteMovieReviewsByOffset.status == "OK") {
                movieReviewDao.insertAll(remoteMovieReviewsByOffset.results.map { it.toMovieReviewEntity() })
            }
            movieReviewDao.get(offset = offset)
        } else {
            localMovieReviews
        }
    }
}