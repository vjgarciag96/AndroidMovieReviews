package com.vjgarcia.moviereviews.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_review")
data class MovieReviewEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "display_title") val displayTitle: String,
    @ColumnInfo(name = "mpaa_rating") val mpaaRating: String,
    @ColumnInfo(name = "review_author") val reviewAuthor: String,
    @ColumnInfo(name = "headline") val headline: String,
    @ColumnInfo(name = "review_publication_date") val reviewPublicationDate: String,
    @ColumnInfo(name = "movie_opening_date") val movieOpeningDate: String? = null,
    @ColumnInfo(name = "link_url") val linkUrl: String,
    @ColumnInfo(name = "link_title") val linkTitle: String,
    @ColumnInfo(name = "image_url") val imageUrl: String
)