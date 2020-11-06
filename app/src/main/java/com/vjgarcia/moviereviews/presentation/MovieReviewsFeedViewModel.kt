package com.vjgarcia.moviereviews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewData
import com.vjgarcia.moviereviews.domain.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieReviewsFeedViewModel(
    getMovieReviewsFeedState: GetMovieReviewsFeedState,
    private val loadInitialMovieReviews: LoadInitialMovieReviews,
    private val loadMoreMovieReviews: LoadMoreMovieReviews,
    private val retryMovieReviewsInitialLoad: RetryInitialMovieReviewsLoad
) : ViewModel() {

    private val _showInitialLoading = MutableStateFlow(true)
    val showInitialLoading = _showInitialLoading.asStateFlow()

    private val _showInitialError = MutableStateFlow(false)
    val showInitialError = _showInitialError.asStateFlow()

    private val _showContent = MutableStateFlow(false)
    val showContent = _showContent.asStateFlow()

    private val movieReviewContentCells = MutableStateFlow<List<MovieReviewCell.Content>>(emptyList())
    private val movieReviewAdditionalCells = MutableStateFlow<List<MovieReviewCell>>(emptyList())
    val content = movieReviewContentCells.combine(movieReviewAdditionalCells) { contentCells, additionalCells ->
        contentCells + additionalCells
    }

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

    fun onRetryClicked() {
        viewModelScope.launch {
            retryMovieReviewsInitialLoad()
        }
    }

    private fun onMovieReviewsFeedState(state: MovieReviewsFeedState) {
        when (state) {
            MovieReviewsFeedState.Initial -> onInitialState()
            is MovieReviewsFeedState.InitialMovieReviewsLoaded -> onInitialMovieReviewsLoaded(state)
            MovieReviewsFeedState.InitialError -> onInitialError()
            MovieReviewsFeedState.LoadingMore -> onLoadingMore()
            is MovieReviewsFeedState.AdditionalMovieReviewsLoaded -> onAdditionalMovieReviewsLoaded(
                state
            )
            is MovieReviewsFeedState.AdditionalMovieReviewsLoadError -> onAdditionalMovieReviewsLoadError()
        }
    }

    private fun onInitialState() {
        _showInitialError.value = false
        _showInitialLoading.value = true
    }

    private fun onInitialMovieReviewsLoaded(state: MovieReviewsFeedState.InitialMovieReviewsLoaded) {
        _showInitialLoading.value = false
        _showContent.value = true
        movieReviewContentCells.value += state.movieReviews.map { it.toMovieReview() }
        movieReviewAdditionalCells.value = listOf(MovieReviewCell.LoadMore)
    }

    private fun onInitialError() {
        _showInitialLoading.value = false
        _showInitialError.value = true
    }

    private fun onLoadingMore() {
        movieReviewAdditionalCells.value = listOf(MovieReviewCell.LoadingMore)
    }

    private fun onAdditionalMovieReviewsLoaded(state: MovieReviewsFeedState.AdditionalMovieReviewsLoaded) {
        movieReviewContentCells.value += state.movieReviews.map { it.toMovieReview() }
        movieReviewAdditionalCells.value = listOf(MovieReviewCell.LoadMore)
    }

    private fun onAdditionalMovieReviewsLoadError() {
        movieReviewAdditionalCells.value = listOf(MovieReviewCell.LoadMoreError)
    }

    private fun MovieReviewData.toMovieReview() = MovieReviewCell.Content(
        title = displayTitle,
        image = imageUrl,
        publicationDate = reviewPublicationDate,
        author = reviewAuthor
    )
}