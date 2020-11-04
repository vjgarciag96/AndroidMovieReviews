package com.vjgarcia.moviereviews.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vjgarcia.moviereviews.data.repository.MovieReviewRepository
import kotlinx.coroutines.launch

class MovieReviewsFeedViewModel(
    private val movieReviewRepository: MovieReviewRepository
) : ViewModel() {

    private val _movieReviews = MutableLiveData<List<MovieReview>>()
    val movieReviews: LiveData<List<MovieReview>> = _movieReviews

    init {
        viewModelScope.launch {
            val movieReviews = movieReviewRepository.get()
            _movieReviews.value = movieReviews.map {
                MovieReview(
                    title = it.displayTitle,
                    image = it.imageUrl,
                    publicationDate = it.reviewPublicationDate,
                    author = it.reviewAuthor
                )
            }
        }
    }
}