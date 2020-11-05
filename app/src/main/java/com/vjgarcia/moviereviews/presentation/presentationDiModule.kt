package com.vjgarcia.moviereviews.presentation

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MovieReviewsFeedViewModel(get(), get(), get()) }
}