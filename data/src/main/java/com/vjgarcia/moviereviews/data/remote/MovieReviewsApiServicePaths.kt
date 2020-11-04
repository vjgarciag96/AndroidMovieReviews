package com.vjgarcia.moviereviews.data.remote

import com.vjgarcia.moviereviews.data.BuildConfig

object MovieReviewsApiServicePaths {

    const val criticsPicks = BuildConfig.REVIEWS_CRITIC_PICKS_PATH +
            "?api-key=${BuildConfig.NY_TIMES_API_KEY}"
}