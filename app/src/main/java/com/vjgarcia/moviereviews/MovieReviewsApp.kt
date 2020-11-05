package com.vjgarcia.moviereviews

import android.app.Application
import com.vjgarcia.moviereviews.data.dataModule
import com.vjgarcia.moviereviews.domain.domainModule
import com.vjgarcia.moviereviews.presentation.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieReviewsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieReviewsApp)
            modules(presentationModule, domainModule, dataModule)
        }
    }
}