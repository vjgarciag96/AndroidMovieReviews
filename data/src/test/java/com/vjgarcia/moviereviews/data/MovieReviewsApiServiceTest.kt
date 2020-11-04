package com.vjgarcia.moviereviews.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vjgarcia.moviereviews.data.remote.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.net.HttpURLConnection

class MovieReviewsApiServiceTest {

    private val mockServer = MockWebServer()

    private lateinit var sut: MovieReviewsApiService

    @Before
    fun setUp() {
        mockServer.start()
        val baseUrl = mockServer.url("/")

        sut = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json {     ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MovieReviewsApiService::class.java)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun `movie reviews successful response`() = runBlocking {
        givenASuccessfulMovieReviewsResponse()

        val movieReviews = sut.getCriticsPicksReviews()

        val expectedMovieReviews = MovieReviewsDto(
            status = "OK",
            hasMore = true,
            numResults = 20,
            results = listOf(
                MovieReviewDto(
                    displayTitle = "Residue",
                    mpaaRating = "",
                    byline = "Glenn Kenny",
                    headline = "‘Residue’ Review: When the Old Neighborhood Is No Longer Yours",
                    summaryShort = "In Merawi Gerima’s first feature film, a failed screenwriter returns to Washington, D.C., and finds gentrification has overrun his home.",
                    publicationDate = "2020-09-17",
                    openingDate = null,
                    link = MovieReviewLinkDto(
                        url = "http://www.nytimes.com/2020/09/17/movies/residue-review-Malawi-Gerima.html",
                        suggestedLinkText = "Read the New York Times Review of Residue"
                    ),
                    multimedia = MovieReviewMultimediaDto(
                        src = "https://static01.nyt.com/images/2020/09/17/arts/17residue/17residue-mediumThreeByTwo210-v2.jpg"
                    )
                ),
                MovieReviewDto(
                    displayTitle = "#Alive",
                    mpaaRating = "",
                    byline = "Elisabeth Vincentelli",
                    headline = "‘#Alive’ Review: From Great Graphics, to Graphic",
                    summaryShort = "A laid back gamer is homebound with no food, no weapons, and zombies knocking at the door in this spirited Korean twist on siege movies.",
                    publicationDate = "2020-09-11",
                    openingDate = "2020-09-08",
                    link = MovieReviewLinkDto(
                        url = "http://www.nytimes.com/2020/09/11/movies/alive-review.html",
                        suggestedLinkText = "Read the New York Times Review of #Alive"
                    ),
                    multimedia = MovieReviewMultimediaDto(
                        src = "https://static01.nyt.com/images/2020/09/11/arts/alive1/alive1-mediumThreeByTwo210.jpg"
                    )
                )
            ),
        )
        assertEquals(expectedMovieReviews, movieReviews)
    }

    private fun givenASuccessfulMovieReviewsResponse() {
        val responseBody = """
                {
                  "status": "OK",
                  "copyright": "Copyright (c) 2020 The New York Times Company. All Rights Reserved.",
                  "has_more": true,
                  "num_results": 20,
                  "results": [
                    {
                      "display_title": "Residue",
                      "mpaa_rating": "",
                      "critics_pick": 1,
                      "byline": "Glenn Kenny",
                      "headline": "‘Residue’ Review: When the Old Neighborhood Is No Longer Yours",
                      "summary_short": "In Merawi Gerima’s first feature film, a failed screenwriter returns to Washington, D.C., and finds gentrification has overrun his home.",
                      "publication_date": "2020-09-17",
                      "opening_date": null,
                      "date_updated": "2020-09-17 11:04:06",
                      "link": {
                        "type": "article",
                        "url": "http://www.nytimes.com/2020/09/17/movies/residue-review-Malawi-Gerima.html",
                        "suggested_link_text": "Read the New York Times Review of Residue"
                      },
                      "multimedia": {
                        "type": "mediumThreeByTwo210",
                        "src": "https://static01.nyt.com/images/2020/09/17/arts/17residue/17residue-mediumThreeByTwo210-v2.jpg",
                        "width": 210,
                        "height": 140
                      }
                    },
                    {
                      "display_title": "#Alive",
                      "mpaa_rating": "",
                      "critics_pick": 1,
                      "byline": "Elisabeth Vincentelli",
                      "headline": "‘#Alive’ Review: From Great Graphics, to Graphic",
                      "summary_short": "A laid back gamer is homebound with no food, no weapons, and zombies knocking at the door in this spirited Korean twist on siege movies.",
                      "publication_date": "2020-09-11",
                      "opening_date": "2020-09-08",
                      "date_updated": "2020-09-11 17:56:02",
                      "link": {
                        "type": "article",
                        "url": "http://www.nytimes.com/2020/09/11/movies/alive-review.html",
                        "suggested_link_text": "Read the New York Times Review of #Alive"
                      },
                      "multimedia": {
                        "type": "mediumThreeByTwo210",
                        "src": "https://static01.nyt.com/images/2020/09/11/arts/alive1/alive1-mediumThreeByTwo210.jpg",
                        "width": 210,
                        "height": 140
                      }
                    }
                  ]
                }
            """.trimIndent()
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody(responseBody)
        )
    }
}