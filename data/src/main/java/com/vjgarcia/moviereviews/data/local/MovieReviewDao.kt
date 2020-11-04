package com.vjgarcia.moviereviews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieReviewDao {

    @Query("SELECT * FROM movie_review LIMIT :limit OFFSET :offset")
    suspend fun get(limit: Int = 20, offset: Int = 0): List<MovieReviewEntity>

    @Insert
    suspend fun insertAll(movieReviews: List<MovieReviewEntity>)
}