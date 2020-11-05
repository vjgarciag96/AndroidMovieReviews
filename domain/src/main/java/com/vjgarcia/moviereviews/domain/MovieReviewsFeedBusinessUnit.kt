package com.vjgarcia.moviereviews.domain

import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieReviewsFeedBusinessUnit(
    private val movieReviewRepository: MovieReviewRepository
) {

    private val _movieReviewsFeedState = MutableStateFlow<MovieReviewsFeedState>(MovieReviewsFeedState.Initial)
    val movieReviewsFeedState = _movieReviewsFeedState.asStateFlow()

    suspend fun loadInitialMovieReviews() {
        val movieReviews = movieReviewRepository.get()
        _movieReviewsFeedState.value = MovieReviewsFeedState.InitialMovieReviewsLoaded(movieReviews)
    }

    suspend fun loadMoreMovieReviews() {
        val currentState = _movieReviewsFeedState.value
        _movieReviewsFeedState.value = MovieReviewsFeedState.LoadingMore
        val offset = when(currentState) {
            is MovieReviewsFeedState.InitialMovieReviewsLoaded -> currentState.movieReviews.size
            is MovieReviewsFeedState.AdditionalMovieReviewsLoadError -> currentState.offset
            is MovieReviewsFeedState.AdditionalMovieReviewsLoaded -> currentState.offset
            else -> error("unreachable path $currentState")
        }

        val movieReviews = movieReviewRepository.get(offset = offset)
        val newOffset = offset + movieReviews.size
        _movieReviewsFeedState.value = MovieReviewsFeedState.AdditionalMovieReviewsLoaded(movieReviews, newOffset)
    }
}