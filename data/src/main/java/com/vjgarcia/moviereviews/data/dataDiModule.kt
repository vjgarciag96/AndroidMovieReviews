package com.vjgarcia.moviereviews.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.vjgarcia.moviereviews.data.local.MovieReviewsDatabase
import com.vjgarcia.moviereviews.data.remote.MovieReviewsApiService
import com.vjgarcia.moviereviews.data.repository.MovieReviewRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {
    factory { MovieReviewsDatabase.build(androidContext()).movieReviewDao() }
    factory {
        Retrofit.Builder()
            .baseUrl(BuildConfig.NY_TIMES_MOVIES_API_ENDPOINT)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
            }.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(MovieReviewsApiService::class.java)
    }
    factory<com.vjgarcia.moviereviews.dataentrypoint.MovieReviewRepository> { MovieReviewRepository(get(), get()) }
}