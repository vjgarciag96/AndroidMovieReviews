package com.vjgarcia.moviereviews.data

import com.vjgarcia.moviereviews.data.local.MovieReviewEntity
import com.vjgarcia.moviereviews.data.remote.MovieReviewDto

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
