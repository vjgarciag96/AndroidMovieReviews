package com.vjgarcia.moviereviews.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieReviewDao {

    @Query("SELECT * FROM movie_review")
    fun getAll(): List<MovieReviewEntity>

    @Insert
    fun insertAll(movieReviews: List<MovieReviewEntity>)
}