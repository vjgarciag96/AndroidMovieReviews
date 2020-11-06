package com.vjgarcia.moviereviews.domain

import com.vjgarcia.moviereviews.dataentrypoint.GetMovieReviewsResult
import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MovieReviewsFeedBusinessUnit(
    private val movieReviewRepository: MovieReviewRepository
) {

    // offset needed to handle movie reviews pagination
    private var movieReviewsOffset = 0

    private val _movieReviewsFeedState = MutableStateFlow<MovieReviewsFeedState>(
        MovieReviewsFeedState.Initial
    )
    val movieReviewsFeedState = _movieReviewsFeedState
        .asStateFlow()
        .onEach {
            if (it is MovieReviewsFeedState.AdditionalMovieReviewsLoaded ||
                it is MovieReviewsFeedState.AdditionalMovieReviewsLoadError ||
                it is MovieReviewsFeedState.InitialMovieReviewsLoaded ||
                it is MovieReviewsFeedState.InitialError
            ) {
                delay(2000)
            }
        }

    suspend fun loadInitialMovieReviews() {
        val newState = when (val getMovieReviewsResult = movieReviewRepository.get()) {
            is GetMovieReviewsResult.Success -> {
                val movieReviews = getMovieReviewsResult.movieReviews
                movieReviewsOffset += movieReviews.size
                MovieReviewsFeedState.InitialMovieReviewsLoaded(movieReviews)
            }
            GetMovieReviewsResult.Error -> MovieReviewsFeedState.InitialError
        }
        _movieReviewsFeedState.value = newState
    }

    suspend fun loadMoreMovieReviews() {
        _movieReviewsFeedState.value = MovieReviewsFeedState.LoadingMore

        val newState =
            when (val getMovieReviewsResult = movieReviewRepository.get(movieReviewsOffset)) {
                is GetMovieReviewsResult.Success -> {
                    val movieReviews = getMovieReviewsResult.movieReviews
                    movieReviewsOffset += movieReviews.size
                    MovieReviewsFeedState.AdditionalMovieReviewsLoaded(movieReviews)
                }
                GetMovieReviewsResult.Error -> MovieReviewsFeedState.AdditionalMovieReviewsLoadError
            }
        _movieReviewsFeedState.value = newState
    }

    suspend fun retryInitialMovieReviewsLoad() {
        _movieReviewsFeedState.value = MovieReviewsFeedState.Initial
        loadInitialMovieReviews()
    }
}