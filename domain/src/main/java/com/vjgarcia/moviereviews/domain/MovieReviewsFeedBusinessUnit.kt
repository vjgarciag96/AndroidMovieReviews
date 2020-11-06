package com.vjgarcia.moviereviews.domain

import com.vjgarcia.moviereviews.dataentrypoint.GetMovieReviewsResult
import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MovieReviewsFeedBusinessUnit(
    private val movieReviewRepository: MovieReviewRepository
) {

    private val _movieReviewsFeedState =
        MutableStateFlow<MovieReviewsFeedState>(MovieReviewsFeedState.Initial)
    val movieReviewsFeedState = _movieReviewsFeedState.asStateFlow()

    suspend fun loadInitialMovieReviews() {
        val newState = when (val getMovieReviewsResult = movieReviewRepository.get()) {
            is GetMovieReviewsResult.Success -> MovieReviewsFeedState.InitialMovieReviewsLoaded(
                getMovieReviewsResult.movieReviews
            )
            GetMovieReviewsResult.Error -> MovieReviewsFeedState.InitialError
        }
        _movieReviewsFeedState.value = newState
    }

    suspend fun loadMoreMovieReviews() {
        val currentState = _movieReviewsFeedState.value
        _movieReviewsFeedState.value = MovieReviewsFeedState.LoadingMore

        val offset = when (currentState) {
            is MovieReviewsFeedState.InitialMovieReviewsLoaded -> currentState.movieReviews.size
            is MovieReviewsFeedState.AdditionalMovieReviewsLoadError -> currentState.offset
            is MovieReviewsFeedState.AdditionalMovieReviewsLoaded -> currentState.offset
            else -> error("unreachable path $currentState")
        }

        val newState = when (val getMovieReviewsResult = movieReviewRepository.get(offset)) {
            is GetMovieReviewsResult.Success -> {
                val movieReviews = getMovieReviewsResult.movieReviews
                val newOffset = offset + movieReviews.size
                MovieReviewsFeedState.AdditionalMovieReviewsLoaded(
                    movieReviews,
                    newOffset
                )
            }
            GetMovieReviewsResult.Error -> MovieReviewsFeedState.AdditionalMovieReviewsLoadError(
                offset
            )
        }
        _movieReviewsFeedState.value = newState
    }
}