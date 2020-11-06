package com.vjgarcia.moviereviews.data.repository

import com.vjgarcia.moviereviews.data.local.MovieReviewDao
import com.vjgarcia.moviereviews.data.remote.MovieReviewsRemoteDataSource
import com.vjgarcia.moviereviews.data.toMovieReviewData
import com.vjgarcia.moviereviews.data.toMovieReviewEntity
import com.vjgarcia.moviereviews.dataentrypoint.GetMovieReviewsResult
import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewRepository

class MovieReviewRepository(
    private val movieReviewDao: MovieReviewDao,
    private val movieReviewsRemoteDataSource: MovieReviewsRemoteDataSource
) : MovieReviewRepository {

    override suspend fun get(offset: Int, limit: Int): GetMovieReviewsResult {
        // Offset is 0, try to update local data source with fresh movie reviews
        // from remote data source.
        if (offset == 0) {
            val movieReviewsResponse = movieReviewsRemoteDataSource.get()
            if (movieReviewsResponse != null) {
                movieReviewDao.insertAll(movieReviewsResponse.results.map {
                    it.toMovieReviewEntity()
                })
            }
        }

        // Try to get the movie reviews from local data source.
        val localMovieReviews = movieReviewDao.get(offset = offset, limit = limit)

        // Requested movie reviews are available in local data source.
        if (localMovieReviews.count() >= limit) {
            return GetMovieReviewsResult.Success(localMovieReviews.map { it.toMovieReviewData() })
        }

        // Requested movie reviews are not available in local data source and offset
        // is 0 (so we already tried to fetch fresh review from remote data source). Return
        // an error and let upper layers decide how to handle it.
        if (offset == 0) {
            return GetMovieReviewsResult.Error
        }

        // Requested movie reviews are not available in local data source and offset
        // is not 0. Try to fetch the movie reviews from remote data source or return an error.
        val movieReviewsResponse = movieReviewsRemoteDataSource.get(offset = offset)
        return if (movieReviewsResponse != null) {
            movieReviewDao.insertAll(movieReviewsResponse.results.map {
                it.toMovieReviewEntity()
            })
            val updatedLocalMovieReviews = movieReviewDao.get(offset = offset)

            if (updatedLocalMovieReviews.count() < limit) {
                GetMovieReviewsResult.Error
            } else {
                GetMovieReviewsResult.Success(updatedLocalMovieReviews.map { it.toMovieReviewData() })
            }
        } else {
            GetMovieReviewsResult.Error
        }
    }
}