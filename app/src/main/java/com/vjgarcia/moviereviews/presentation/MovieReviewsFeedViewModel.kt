package com.vjgarcia.moviereviews.presentation

import androidx.lifecycle.ViewModel

class MovieReviewsFeedViewModel : ViewModel() {
    val movieReviews = listOf(
        MovieReview(
            title = "Son of the White Mare",
            image = "https://static01.nyt.com/images/2020/08/21/arts/20sonofthewhitemare/merlin_175720962_d51a8da1-7969-4d0b-a021-694959830b53-mediumThreeByTwo210.jpg",
            publicationDate = "2020-08-20",
            author = "Maya Phillips"
        ),
        MovieReview(
            title = "Son of the White Mare 2",
            image = "https://static01.nyt.com/images/2020/08/21/arts/20sonofthewhitemare/merlin_175720962_d51a8da1-7969-4d0b-a021-694959830b53-mediumThreeByTwo210.jpg",
            publicationDate = "2020-08-20",
            author = "Maya Phillips"
        )
    )
}