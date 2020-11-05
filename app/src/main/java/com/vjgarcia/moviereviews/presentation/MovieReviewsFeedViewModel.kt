package com.vjgarcia.moviereviews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewData
import com.vjgarcia.moviereviews.domain.GetMovieReviewsFeedState
import com.vjgarcia.moviereviews.domain.LoadInitialMovieReviews
import com.vjgarcia.moviereviews.domain.LoadMoreMovieReviews
import com.vjgarcia.moviereviews.domain.MovieReviewsFeedState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieReviewsFeedViewModel(
    getMovieReviewsFeedState: GetMovieReviewsFeedState,
    loadInitialMovieReviews: LoadInitialMovieReviews,
    private val loadMoreMovieReviews: LoadMoreMovieReviews
) : ViewModel() {

    private val _movieReviews = MutableStateFlow<List<MovieReview>>(emptyList())
    val movieReviews = _movieReviews.asStateFlow()
    private val _shouldShowLoadMore = MutableStateFlow(false)
    val shouldShowLoadMore = _shouldShowLoadMore.asStateFlow()

    init {
        getMovieReviewsFeedState()
            .onEach { onMovieReviewsFeedState(it) }
            .launchIn(viewModelScope)
        viewModelScope.launch {
            loadInitialMovieReviews()
        }
    }

    fun onLoadMoreClicked() {
        viewModelScope.launch {
            loadMoreMovieReviews()
        }
    }

    private fun onMovieReviewsFeedState(state: MovieReviewsFeedState) {
        when (state) {
            MovieReviewsFeedState.Initial -> _shouldShowLoadMore.value = false
            is MovieReviewsFeedState.InitialMovieReviewsLoaded -> {
                _movieReviews.value = state.movieReviews.map { it.toMovieReview() }
                _shouldShowLoadMore.value = true
            }
            MovieReviewsFeedState.InitialError -> Unit
            MovieReviewsFeedState.LoadingMore -> _shouldShowLoadMore.value = false
            is MovieReviewsFeedState.AdditionalMovieReviewsLoaded -> {
                _movieReviews.value = _movieReviews.value + state.movieReviews.map { it.toMovieReview() }
                _shouldShowLoadMore.value = true
            }
            is MovieReviewsFeedState.AdditionalMovieReviewsLoadError -> Unit
        }
    }

    private fun MovieReviewData.toMovieReview() = MovieReview(
        title = displayTitle,
        image = imageUrl,
        publicationDate = reviewPublicationDate,
        author = reviewAuthor
    )
}