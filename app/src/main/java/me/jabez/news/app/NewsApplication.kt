package me.jabez.news.app

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatDelegate

class NewsApplication : Application() {
    companion object {
        lateinit var instance: NewsApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}