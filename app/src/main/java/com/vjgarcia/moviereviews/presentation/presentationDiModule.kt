package com.vjgarcia.moviereviews.presentation

import androidx.navigation.NavController
import com.vjgarcia.moviereviews.AppNavigation
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val presentationModule = module {
    viewModel { (navController: NavController) ->
        MovieReviewsFeedViewModel(
            get(),
            get(),
            get(),
            get(),
            get { parametersOf(navController) }
        )
    }
    factory { (navController: NavController) -> AppNavigation(navController) }
}