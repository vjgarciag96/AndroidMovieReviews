package com.vjgarcia.moviereviews

import MovieReviewsFeed
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.vjgarcia.moviereviews.presentation.MovieReviewsFeedViewModel
import com.vjgarcia.moviereviews.ui.MovieReviewDetail
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = AppNavigation.MOVIE_REVIEWS_FEED,
            ) {
                composable(AppNavigation.MOVIE_REVIEWS_FEED) {
                    val movieReviewsViewModel by viewModel<MovieReviewsFeedViewModel> { parametersOf(navController) }
                    MovieReviewsFeed(viewModel = movieReviewsViewModel)
                }
                composable(
                    AppNavigation.MOVIE_REVIEW_DETAIL,
                    arguments = listOf(navArgument(AppNavigation.MOVIE_REVIEW_ID_ARG) {
                        type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    val movieReviewId = navBackStackEntry.arguments?.getInt(AppNavigation.MOVIE_REVIEW_ID_ARG)
                        ?: error("movieReviewId must be available")
                    MovieReviewDetail(movieReviewId = movieReviewId)
                }
            }
        }
    }
}

