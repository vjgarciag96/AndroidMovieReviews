package com.vjgarcia.moviereviews.data.repository

import com.vjgarcia.moviereviews.data.local.MovieReviewDao
import com.vjgarcia.moviereviews.data.remote.MovieReviewsApiService
import com.vjgarcia.moviereviews.data.toMovieReviewData
import com.vjgarcia.moviereviews.data.toMovieReviewEntity
import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewData
import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewRepository

class MovieReviewRepository(
    private val movieReviewDao: MovieReviewDao,
    private val movieReviewsApiService: MovieReviewsApiService
) : MovieReviewRepository {

     override suspend fun get(offset: Int, limit: Int): List<MovieReviewData> {
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
            movieReviewDao.get(offset = offset).map { it.toMovieReviewData() }
        } else {
            localMovieReviews.map { it.toMovieReviewData() }
        }
    }
}