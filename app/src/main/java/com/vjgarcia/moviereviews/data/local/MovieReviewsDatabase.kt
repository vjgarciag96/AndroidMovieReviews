package com.vjgarcia.moviereviews.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MovieReviewEntity::class],
    version = 1
)
abstract class MovieReviewsDatabase : RoomDatabase() {
    abstract fun movieReviewDao(): MovieReviewDao

    companion object {
        private const val DATABASE_NAME = "movie-review-db"

        fun build(applicationContext: Context) = Room.databaseBuilder(
            applicationContext,
            MovieReviewsDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}