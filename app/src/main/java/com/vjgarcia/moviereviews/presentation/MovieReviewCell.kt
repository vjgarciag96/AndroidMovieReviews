package com.vjgarcia.moviereviews.presentation

sealed class MovieReviewCell {
    data class Content(
        val title: String,
        val image: String,
        val publicationDate: String,
        val author: String
    ) : MovieReviewCell()
    object LoadMore : MovieReviewCell()
    object LoadingMore : MovieReviewCell()
    object LoadMoreError : MovieReviewCell()
}