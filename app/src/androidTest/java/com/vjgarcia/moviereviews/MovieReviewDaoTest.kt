package com.vjgarcia.moviereviews

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.vjgarcia.moviereviews.data.local.MovieReviewDao
import com.vjgarcia.moviereviews.data.local.MovieReviewEntity
import com.vjgarcia.moviereviews.data.local.MovieReviewsDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class MovieReviewDaoTest {

    private lateinit var database: MovieReviewsDatabase

    private lateinit var sut: MovieReviewDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MovieReviewsDatabase::class.java).build()

        sut = database.movieReviewDao()
    }

    @Test
    fun getsAll_emptyMovieReviews() {
        val movieReviews = sut.getAll()

        assertTrue(movieReviews.isEmpty())
    }

    @Test
    fun writesAnyMovieReviews_getsAnyMovieReviews() {
        val anyMovieReviews = anyMovieReviews()

        sut.insertAll(anyMovieReviews)
        val movieReviews = sut.getAll()

        assertEquals(anyMovieReviews, movieReviews)
    }

    private fun anyMovieReviews() = listOf(
        MovieReviewEntity(
            id = 1,
            displayTitle = "displayTitle 1",
            mpaaRating = "+18",
            reviewAuthor = "Víctor J.",
            headline = "This is a STRONG headline",
            reviewPublicationDate = "2020-09-23",
            movieOpeningDate = "2020-09-22",
            linkUrl = "https://www.github.com/vjgarciag96",
            linkTitle = "SPAM",
            imageUrl = "https://www.github.com/vjgarciag96/invent.png",
        ),
        MovieReviewEntity(
            id = 2,
            displayTitle = "displayTitle 2",
            mpaaRating = "+55",
            reviewAuthor = "Julián V.",
            headline = "This is a wrong headline",
            reviewPublicationDate = "2020-09-22",
            movieOpeningDate = "2020-09-23",
            linkUrl = "https://www.github.com/vjgarciag95",
            linkTitle = "MAPS",
            imageUrl = "https://www.github.com/vjgarciag95/invent.png",
        )
    )
}