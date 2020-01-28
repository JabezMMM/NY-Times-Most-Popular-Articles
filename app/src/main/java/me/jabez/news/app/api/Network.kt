package me.jabez.news.app.api

import me.jabez.news.app.BuildConfig
import me.jabez.news.app.model.MostViewedResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

object Network {
    private fun getRetrofitInstance(): Retrofit {
        val interceptor: HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }

        val client: OkHttpClient =
            OkHttpClient.Builder().apply { this.addInterceptor(interceptor) }.build()

        val baseUrl = "https://api.nytimes.com/svc/mostpopular/v2/"

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getMostPopularService(): MostPopular {
        return getRetrofitInstance().create()
    }
}

/**
 * Fetch all the latest news article from various news services
 * using the News API.
 */
interface MostPopular {
    /**
     * Retrieves all the latest news article from Google news using News API.
     */
    @GET("mostviewed/all-sections/{period}.json?api-key=${BuildConfig.API_KEY}")
    fun getArticles(@Path("period") period: Int): Call<MostViewedResponse>
}