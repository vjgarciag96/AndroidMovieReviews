package com.vjgarcia.moviereviews.dataentrypoint

data class MovieReviewData(
    val id: Int = 0,
    val displayTitle: String,
    val mpaaRating: String,
    val reviewAuthor: String,
    val headline: String,
    val reviewPublicationDate: String,
    val movieOpeningDate: String? = null,
    val linkUrl: String,
    val linkTitle: String,
    val imageUrl: String
)