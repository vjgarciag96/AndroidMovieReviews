package com.vjgarcia.moviereviews.domain

import org.koin.dsl.module

val domainModule = module {
    single { MovieReviewsFeedBusinessUnit(get()) }
    factory { GetMovieReviewsFeedState(get()) }
    factory { LoadInitialMovieReviews(get()) }
    factory { LoadMoreMovieReviews(get()) }
}