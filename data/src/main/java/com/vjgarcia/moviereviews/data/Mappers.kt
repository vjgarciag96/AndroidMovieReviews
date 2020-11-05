package com.vjgarcia.moviereviews.data

import com.vjgarcia.moviereviews.data.local.MovieReviewEntity
import com.vjgarcia.moviereviews.data.remote.MovieReviewDto
import com.vjgarcia.moviereviews.dataentrypoint.MovieReviewData

fun MovieReviewDto.toMovieReviewEntity() = MovieReviewEntity(
    displayTitle = displayTitle,
    mpaaRating = mpaaRating,
    reviewAuthor = byline,
    headline = headline,
    reviewPublicationDate = publicationDate,
    movieOpeningDate = openingDate,
    linkUrl = link.url,
    linkTitle = link.suggestedLinkText,
    imageUrl = multimedia.src
)

fun MovieReviewEntity.toMovieReviewData() = MovieReviewData(
    id,
    displayTitle,
    mpaaRating,
    reviewAuthor,
    headline,
    reviewPublicationDate,
    movieOpeningDate,
    linkUrl,
    linkTitle,
    imageUrl
)
