package com.vjgarcia.moviereviews.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewsDto(
    @SerialName("status") val status: String,
    @SerialName("has_more") val hasMore: Boolean,
    @SerialName("num_results") val numResults: Int,
    @SerialName("results") val results: List<MovieReviewDto>
)

@Serializable
data class MovieReviewDto(
    @SerialName("display_title") val displayTitle: String,
    @SerialName("mpaa_rating") val mpaaRating: String,
    @SerialName("byline") val byline: String,
    @SerialName("headline") val headline: String,
    @SerialName("summary_short") val summaryShort: String,
    @SerialName("publication_date") val publicationDate: String,
    @SerialName("opening_date") val openingDate: String? = null,
    @SerialName("link") val link: MovieReviewLinkDto,
    @SerialName("multimedia") val multimedia: MovieReviewMultimediaDto
)

@Serializable
data class MovieReviewLinkDto(
    @SerialName("url") val url: String,
    @SerialName("suggested_link_text") val suggestedLinkText: String
)

@Serializable
data class MovieReviewMultimediaDto(
    @SerialName("src") val src: String
)